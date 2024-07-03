package com.a88.utils;

import com.a88.Pojo.cloudStorage;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class uploadFileUtil {
//    //@Value("${google.cloudStorage.projectID}")
//    private String projectID;
//    //@Value("${google.cloudStorage.bucketName}")
//    private String bucketName;

    @Autowired
    private cloudStorage storage;

    // Method to delete old file from Cloud Storage
    private Storage getStorageService() {
        String projectID = storage.getProjectID();
        return StorageOptions.newBuilder().setProjectId(projectID).build().getService();
    }

    // Method to delete old file from Cloud Storage
    public void deleteFile(String oldFileName) {
        String bucketName = storage.getBucketName();
        Storage storageService = getStorageService();

        BlobId blobId = BlobId.of(bucketName, oldFileName);
        boolean deleted = storageService.delete(blobId);

        if (deleted) {
            log.info("File was deleted from {} and file name is {}", bucketName, oldFileName);
            System.out.println("File " + oldFileName + " was deleted from bucket " + bucketName);
        } else {
            System.out.println("File " + oldFileName + " not found in bucket " + bucketName);
        }
    }

    public void deleteFiles(String[] oldFileNames) {
        for (String fileName : oldFileNames) {
            deleteFile(fileName);
        }
    }
    public String uploadFile(MultipartFile file) throws IOException {

        String bucketName = storage.getBucketName();
        String projectID = storage.getProjectID();

        // get service
        Storage storage = StorageOptions.newBuilder().setProjectId(projectID)
                .build().getService();
        // use UUID to set only one fileName
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String extname = originalFilename.substring(index);
        String newFileName = UUID.randomUUID().toString() + extname;


        BlobId blobId = BlobId.of(bucketName, newFileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();


        // upload to Cloud Storage
        storage.createFrom(blobInfo, file.getInputStream());
        // file's access url
        String url = "https://storage.googleapis.com/" + bucketName + "/" + newFileName;

        System.out.println("File " + file.getOriginalFilename() + " uploaded to bucket " + bucketName + " as " + newFileName + " file url is: " + url);

        return url;
    }
}
