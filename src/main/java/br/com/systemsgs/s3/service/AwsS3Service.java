package br.com.systemsgs.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class AwsS3Service {

    private final AmazonS3 amazonS3Client;

    public AwsS3Service(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    /* Lista os Buckets que foram criados por você na Aws */
    public List<Bucket> listarBuckets(){
        return amazonS3Client.listBuckets();
    }

    /* Cria um novo Bucket */
    public void criaBucketS3(String bucketName) {
        if(amazonS3Client.doesBucketExist(bucketName)) {
            log.info("Já possui um Bucket com esse nome!");
            return;
        }
        amazonS3Client.createBucket(bucketName);
    }

    /*Deleta um Bucket pelo Nome*/
    public void deleteBucket(String bucketName){
        try {
            amazonS3Client.deleteBucket(bucketName);
        } catch (AmazonServiceException e) {
            log.error(e.getErrorMessage());
            return;
        }
    }

    /* Lista Objetos pelo nome do Bucket */
    public List<S3ObjectSummary> listaObjetos(String bucketName){
        ObjectListing objectListing = amazonS3Client.listObjects(bucketName);
        return objectListing.getObjectSummaries();
    }

    /*Download de um Arquivo no S3 - primeiro busca pelo bucket e nome do arquivo*/
    public void downloadObject(String bucketName, String objectName){
        S3Object s3object = amazonS3Client.getObject(bucketName, objectName);
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        try {
            FileUtils.copyInputStreamToFile(inputStream, new File("." + File.separator + objectName));
        } catch (IOException e) {
            log.error("Erro ao realizar Download do Arquivo " + e.getMessage());
        }
    }

    /* Deleta arquivo no S3 - pelo Bucket e nome do arquivo */
    public void deletaArquivo(String bucketName, String objectName){
        amazonS3Client.deleteObject(bucketName, objectName);
    }

}
