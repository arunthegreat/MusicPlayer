package com.arun.practice.medialib.model;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaData {

    @Id
    private String id;
    private String fileName;
    private String artist;
    private String album;
    private String title;
    private String duration;
    private String albumArtist;
    private Binary coverImage;

}
