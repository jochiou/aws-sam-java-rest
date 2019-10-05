package com.amazonaws.dao;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class S3Dao {
    private AmazonS3 s3;


    @Inject
    public S3Dao(AmazonS3 s3){
        this.s3 = s3;
        /*
        s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_2)
                .build();
        */
    }


    /*
    * this method generates an url that allows clients to communicate with S3
    * */
    // return pre-signed url
    // objKey = file path (without "....bucket")
    // encrypKey has to be in KMS
    public String generatePreSignedUrl(String prefix, String objectName, String bucket, String encrypKey){
        String key = prefix + objectName;
        GeneratePresignedUrlRequest genreq = new GeneratePresignedUrlRequest(bucket, key, HttpMethod.POST)
                .withKmsCmkId(encrypKey);
        URL url = s3.generatePresignedUrl(genreq);
        return url.toString();
    }
}
