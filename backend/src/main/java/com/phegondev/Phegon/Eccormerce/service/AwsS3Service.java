package com.phegondev.Phegon.Eccormerce.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class GcsService {

    private final String bucketName = "vamsi-bucket-6281";

    // The path to your service account key file
    @Value("${gcp.credentials.file.path}")
    private String gcpCredentialsFilePath;

    public String saveImageToGcs(MultipartFile photo) {
        try {
            String gcsFileName = photo.getOriginalFilename();
            InputStream inputStream = photo.getInputStream();

            // Initialize GCS client
            Storage storage = StorageOptions.newBuilder()
                    .setProjectId("harvesthub-443202")
                    .build()
                    .getService();

            // Set metadata
            BlobId blobId = BlobId.of(bucketName, gcsFileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType("image/jpeg")
                    .build();

            // Upload file
            storage.create(blobInfo, inputStream);

            return "https://storage.googleapis.com/" + vamsi-bucket-6281 + "/" + gcsFileName;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to upload image to GCS bucket: " + e.getMessage());
        }
    }
}
