package com.arun.practice.medialib.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.ImmutableList;

import ealvatag.tag.TagField;

@Component
public class MP3FieldDataExtractor extends FieldDataExtractor {

	@Override
	public String getFieldData(ImmutableList<TagField> dataList) {
		String fieldData = null;
		if (!CollectionUtils.isEmpty(dataList)) {
			fieldData = dataList.get(0).toString();
			Pattern pattern = Pattern.compile("Text=\"(.*?)\"");
			Matcher matcher = pattern.matcher(fieldData);
			if (matcher.find()) {
				fieldData = matcher.group(1);
			}
		}
		return fieldData;
	}

}
