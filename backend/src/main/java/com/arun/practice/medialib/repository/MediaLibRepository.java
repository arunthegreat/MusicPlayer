package com.arun.practice.medialib.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.arun.practice.medialib.model.MetaData;

@Repository
public interface MediaLibRepository extends MongoRepository<MetaData, String> {

    @Query("{fileName: ?0}")
    Optional<MetaData> findByFileName(String fileName);

    @Query("{ title: { $ne : null } }")
    List<MetaData> getMediaList();

}