package br.com.systemsgs.s3.controller;

import br.com.systemsgs.s3.service.AwsS3Service;
import com.amazonaws.services.s3.model.Bucket;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/bucket")
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    public AwsS3Controller(AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    @PostMapping(value = "/{bucketName}")
    public void criaBucket(@PathVariable String bucketName){
        awsS3Service.criaBucketS3(bucketName);
    }

    @GetMapping(value = "/listaBuckets")
    public List<String> listBuckets(){
        var buckets = awsS3Service.listarBuckets();
        var names = buckets.stream().map(Bucket::getName).collect(Collectors.toList());
        return names;
    }

}
