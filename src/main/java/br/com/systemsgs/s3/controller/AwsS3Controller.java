package br.com.systemsgs.s3.controller;

import br.com.systemsgs.s3.service.AwsS3Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/bucket")
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    public AwsS3Controller(AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    @PostMapping(value = "/{bucketName}")
    public void createBucket(@PathVariable String bucketName){
        awsS3Service.criaBucketS3(bucketName);
    }

}
