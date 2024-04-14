package com.nurikov.tasklist.service.impl;

import com.nurikov.tasklist.domain.exception.ImageUploadException;
import com.nurikov.tasklist.domain.task.TaskImage;
import com.nurikov.tasklist.service.ImageService;
import com.nurikov.tasklist.service.props.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public String upload(TaskImage image) {
        try {
            createBucket();
        } catch (Exception exception) {
            throw new ImageUploadException("Image upload file" + exception.getMessage());
        }
        MultipartFile multipartFile = image.getMultipartFile();
        if (multipartFile.isEmpty() || multipartFile.getOriginalFilename() == null)
            throw new ImageUploadException("Image must have name");
        String fileName = generateName(multipartFile);
        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (Exception exception) {
            throw new ImageUploadException("Image upload file" + exception.getMessage());
        }
        saveImage(inputStream, fileName);
        return fileName;
    }

    @SneakyThrows
    private void saveImage(InputStream inputStream, String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(fileName)
                .build());
    }

    @SneakyThrows
    private void createBucket() {
        boolean bucket = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());
        if (!bucket) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    private String generateName(MultipartFile multipartFile) {
        String extension = getExtension(multipartFile);
        return UUID.randomUUID() + "." + extension;
    }

    private String getExtension(MultipartFile multipartFile) {
        return multipartFile.getOriginalFilename()
                .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
    }

}
