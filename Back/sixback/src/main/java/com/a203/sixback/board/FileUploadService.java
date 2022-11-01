package com.a203.sixback.board;

import com.amazonaws.AmazonClientException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileUploadService {

    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl;

    @Value("${cloud.aws.s3.bucket.value}")
    public String bucket;

    private final AmazonS3Client amazonS3Client;

    public String upload(MultipartFile uploadFile) throws IOException {
        String origName = uploadFile.getOriginalFilename();
        System.out.println(origName);
        String url;
        try {
            final String ext = origName.substring(origName.lastIndexOf('.'));
            final String saveFileName = getUuid() + ext;

            File file = new File(System.getProperty("user.dir") + saveFileName);
            System.out.println("saveFileName: " + saveFileName);
            uploadFile.transferTo(file);
            uploadOnS3(saveFileName, file);
            url = defaultUrl + saveFileName;
            System.out.println(defaultUrl);
            System.out.println(url);
            file.delete();
        } catch (StringIndexOutOfBoundsException e) {
            url = null;
        }
        return url;
    }
    private String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private void uploadOnS3(final String findName, final File file) {
        final TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(this.amazonS3Client).build();

        final PutObjectRequest request = new PutObjectRequest(bucket, findName, file);

        final Upload upload = transferManager.upload(request);

        try {
            upload.waitForCompletion();
        } catch (AmazonClientException | InterruptedException amazonClientException) {
            amazonClientException.printStackTrace();
        }
    }

    public void delete(String imageUrl) {
        try {
            final String deleteFileName = imageUrl.substring(imageUrl.lastIndexOf('/')+1);
            amazonS3Client.deleteObject(this.bucket, deleteFileName);
        } catch (SdkClientException e){
            e.printStackTrace();
        }
    }
}
