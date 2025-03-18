package com.arun.practice.medialib.util;

import java.io.UnsupportedEncodingException;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;

import com.google.common.collect.ImmutableList;

import ealvatag.tag.Tag;
import ealvatag.tag.TagField;

public abstract class FieldDataExtractor {
    public abstract String getFieldData(ImmutableList<TagField> dataList);

    public Binary getImageData(Tag t) throws UnsupportedEncodingException {
        return new Binary(BsonBinarySubType.BINARY, t.getArtworkList().get(0).getBinaryData());
    }
}
