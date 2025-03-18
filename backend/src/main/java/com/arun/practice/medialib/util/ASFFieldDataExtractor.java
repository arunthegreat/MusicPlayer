package com.arun.practice.medialib.util;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.ImmutableList;

import ealvatag.tag.TagField;

@Component
public class ASFFieldDataExtractor extends FieldDataExtractor {

    @Override
    public String getFieldData(ImmutableList<TagField> dataList) {
        String fieldData = null;
        if (!CollectionUtils.isEmpty(dataList)) {
            fieldData = dataList.get(0).toString();
        }
        return fieldData;
    }

}
