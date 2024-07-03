package com.a88.Pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "google.cloud-storage")
public class cloudStorage {
    private String projectID;
    private String bucketName;

}
