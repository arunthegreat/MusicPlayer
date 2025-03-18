package com.arun.practice.medialib.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.arun.practice.medialib.model.MetaData;
import com.arun.practice.medialib.repository.MediaLibRepository;
import com.arun.practice.medialib.rest.model.ViewMetaData;
import com.arun.practice.medialib.util.AudioMetaDataExtractor;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MediaLibService {

    @Value("${medialib.exclude.extensions}")
    private List<String> excludeExtensions;

    private final Logger logger = LoggerFactory.getLogger(MediaLibService.class);

    @Autowired
    private MediaLibRepository mediaLibRepository;

    @Autowired
    private AudioMetaDataExtractor audioMetaDataExtractor;

    public MetaData getMediaMetaData(String mediaFile) throws Exception {
        return audioMetaDataExtractor.extractMetaData(mediaFile);
    }

    public void getMediaMetaData() throws Exception {
        File dir = new File("C:/Arun/Personal/Songs");
        File[] files = dir.listFiles();
        int i = 1;
        logger.info("{} Media Files in the Directory: {}", dir.listFiles().length, dir.getPath());
        for (File file : files) {
            if (excludeExtensions.contains(file.getName().split("\\.")[1].toLowerCase())) {
                continue;
            }
            String fileName = file.getName();
            Optional<MetaData> metaData = mediaLibRepository.findByFileName(file.getPath());
            if (metaData.isPresent()) {
                logger.info("File {} already processed with metadata {}", fileName, metaData.get());
            } else {
                logger.info("Processing file {} - File Name {}", i++, fileName);
                mediaLibRepository.save(getMediaMetaData(file.getAbsolutePath()));
                logger.info("Successfully Processed the Metadata for File: {}", fileName);
            }
        }
    }

    public List<ViewMetaData> getAllMediaMetaData() {
        List<MetaData> dbMetaList = mediaLibRepository.findAll();
        List<ViewMetaData> viewMetaList = dbMetaList.stream().map(metaData -> {
            ViewMetaData viewMetaData = new ViewMetaData();
            viewMetaData.setId(metaData.getId());
            viewMetaData.setFileName(metaData.getFileName());
            viewMetaData.setTitle(metaData.getTitle());
            viewMetaData.setArtist(metaData.getArtist());
            viewMetaData.setAlbum(metaData.getAlbum());
            viewMetaData.setDuration(metaData.getDuration());
            return viewMetaData;
        }).collect(Collectors.toList());
        return viewMetaList;
    }

    public byte[] getMediaFile(String id) {
        Optional<MetaData> metaData = mediaLibRepository.findById(id);
        byte[] image = null;
        if (metaData.isPresent()) {

            logger.info("Getting the file for id: {}", id);
            String filePath = metaData.get().getFileName();
            logger.info("File Path for id {} is: {}", id, filePath);
            try {
                image = Files.readAllBytes(new File(filePath).toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("Successfully retrieved the file for id: {}", id);
        return image;
    }

    public byte[] getImageFile(String id) {
        Optional<MetaData> metaData = mediaLibRepository.findById(id);
        byte[] image = null;
        if (metaData.isPresent()) {

            logger.info("Getting the Image file for id: {}", id);
            Binary imageData = metaData.get().getCoverImage();
            image = imageData.getData();
        }
        logger.info("Successfully retrieved the Image Data for id: {}", id);
        return image;
    }

}
