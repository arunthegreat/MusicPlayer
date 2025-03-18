package com.arun.practice.medialib.rest.model;

import lombok.Data;

@Data
public class ViewMetaData {

    private String id;
    private String fileName;
    private String title;
    private String artist;
    private String album;
    private String duration;
    private String coverImage;

}
