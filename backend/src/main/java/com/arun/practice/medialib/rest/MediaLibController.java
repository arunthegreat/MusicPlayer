package com.arun.practice.medialib.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import com.arun.practice.medialib.util.ASFFieldDataExtractor;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.arun.practice.medialib.model.MetaData;
import com.arun.practice.medialib.rest.model.ViewMetaData;
import com.arun.practice.medialib.service.MediaLibService;

@RestController()
@RequestMapping("/medialib/api")
public class MediaLibController {

    private final Logger logger = LoggerFactory.getLogger(MediaLibController.class);

    @Autowired
    private MediaLibService mediaLibService;

    MediaLibController(ASFFieldDataExtractor ASFFieldDataExtractor) {
    }

    @GetMapping("/findall")
    @CrossOrigin(origins = "http://localhost:5173")
    public List<ViewMetaData> getAudioMetaData() {
        return mediaLibService.getAllMediaMetaData();
    }

    @GetMapping("/song/{id}")
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", methods = { RequestMethod.GET })
    public ResponseEntity<?> getSong(@PathVariable String id) throws IOException {
        logger.info("Getting the song for id: {}", id);
        byte[] fileData = mediaLibService.getMediaFile(id);
        if (fileData == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileData);
    }

    @GetMapping("/image/{id}")
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", methods = { RequestMethod.GET })
    public ResponseEntity<?> getImage(@PathVariable String id) throws IOException {
        logger.info("Getting the image for id: {}", id);
        byte[] image = mediaLibService.getImageFile(id);
        if (image == null) {
            logger.info("Image not found for id: {}", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(Base64.getEncoder().encodeToString(image));
    }

    @GetMapping("/metadata")
    public String getMetaData() {
        try {
            mediaLibService.getMediaMetaData();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to Process the Metadata";
        }
        return "Successfully Processed the Metadata";
    }

    @GetMapping("/health")
    public String healthCheck() {
        File dir = new File("C:/Arun/Personal/Songs");
        logger.info("{} Media Files in the Directory: {}", dir.listFiles().length, dir.getPath());
        return "Media Library Application Running Successfully";
    }
}
