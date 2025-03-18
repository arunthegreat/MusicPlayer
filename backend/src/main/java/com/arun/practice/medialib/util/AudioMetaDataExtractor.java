package com.arun.practice.medialib.util;

import java.io.File;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import ealvatag.tag.FieldKey;
import ealvatag.tag.Tag;
import ealvatag.tag.TagField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.arun.practice.medialib.model.MetaData;

import ealvatag.audio.AudioFile;
import ealvatag.audio.AudioFileIO;
import ealvatag.audio.AudioHeader;

@Component
public class AudioMetaDataExtractor {

    @Autowired
    FieldDataExtractorFactory fieldDataExtractorFactory;
    
    private final Logger logger = LoggerFactory.getLogger(AudioMetaDataExtractor.class);

    public MetaData extractMetaData(String filePath) throws Exception {
        MetaData metaData = new MetaData();
        metaData.setId(UUID.randomUUID().toString());
        AudioFile audioFile = AudioFileIO.read(new File(filePath));
        final AudioHeader audioHeader = audioFile.getAudioHeader();

        FieldDataExtractor fieldDataExtractor = fieldDataExtractorFactory
                .getFieldDataExtractor(audioHeader.getEncodingType());

        metaData.setFileName(filePath);
        metaData.setDuration(formatTime(audioHeader.getDuration(TimeUnit.MILLISECONDS, false)));
        Optional<Tag> tag = audioFile.getTag();
        if (tag.isPresent()) {

            Tag t = tag.get();
            ImmutableSet<FieldKey> supportedFields = t.getSupportedFields();

            if (supportedFields.contains(FieldKey.TITLE)) {
                metaData.setTitle(fieldDataExtractor.getFieldData(t.getFields(FieldKey.TITLE)));
            }
            if (supportedFields.contains(FieldKey.ARTIST)) {
                metaData.setArtist(fieldDataExtractor.getFieldData(t.getFields(FieldKey.ARTIST)));
            }
            if (supportedFields.contains(FieldKey.ALBUM)) {
                metaData.setAlbum(fieldDataExtractor.getFieldData(t.getFields(FieldKey.ALBUM)));
            }

            if (supportedFields.contains(FieldKey.COVER_ART)) {
                ImmutableList<TagField> coverArt = t.getFields(FieldKey.COVER_ART);
                if (!CollectionUtils.isEmpty(coverArt)) {
                    logger.info("Cover Art found for file: {}", filePath);
                    metaData.setCoverImage(fieldDataExtractor.getImageData(t));
                }
            }
        }

        return metaData;
    }

    protected String formatTime(long time) {
        long seconds = Duration.ofMillis(time).getSeconds();

        long HH = seconds / 3600;
        long MM = (seconds % 3600) / 60;
        long SS = seconds % 60;

        String duration = null;

        if (HH > 0) {
            duration = String.format("%02d:%02d:%02d", HH, MM, SS);
        } else if (MM > 0) {
            duration = String.format("%02d:%02d", MM, SS);
        } else {
            duration = String.format("0:%02d", SS);
        }

        return duration;
    }

}
