package com.arun.practice.medialib.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldDataExtractorFactory {

	@Autowired
	MP3FieldDataExtractor mp3FieldDataExtractor;
	
	@Autowired
	ASFFieldDataExtractor asfFieldDataExtractor;

	public FieldDataExtractor getFieldDataExtractor(String encodingType) {
		FieldDataExtractor extractor = null;
		if (encodingType.toLowerCase().contains("mp3")) {
			extractor = mp3FieldDataExtractor;
		} else if (encodingType.toLowerCase().contains("asf")) {
			extractor = asfFieldDataExtractor;
		}
		return extractor;
	}
}
