package com.vb.services;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.vb.api.service.IStorageService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vb.utils.ContentTypeUtils.checkContentType;
import static com.vb.utils.ContentTypeUtils.convertImageContentTypeToExtension;
import static org.apache.http.entity.ContentType.*;


@Service
public class StorageService implements IStorageService {

    @Autowired
    private Storage storage;

    @Value("${gcp.storage.bucket.images.name}")
    private String imagesBucketName;

    @Value("${gcp.storage.bucket.images.url}")
    private String imagesBucketUrl;

    @Override
    public void saveCarImages(Long carId, List<MultipartFile> images, List<String> imagesUrls) {
        images.forEach(image -> saveCarImage(carId, image, imagesUrls));
    }

    @Override
    public void saveCarImage(Long carId, MultipartFile image, List<String> imagesUrls) {
        String imageName = generateImageName(carId, image);
        String imageUrl = imagesBucketUrl + imageName;
        while (imagesUrls.contains(imageUrl)) {
            imageName = generateImageName(carId, image);
            imageUrl = imagesBucketUrl + imageName;
        }
        BlobInfo blobInfo = BlobInfo.newBuilder(imagesBucketName, imageName)
                .setContentType(image.getContentType()).build();
        try {
            storage.create(blobInfo, image.getBytes());
            imagesUrls.add(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteImage(String imageUrl) {
        BlobId blobId = BlobId.of(imagesBucketName, determineImageName(imageUrl));
        return storage.delete(blobId);

    }

    private String determineImageName(String imageUrl) {
        Pattern pattern = Pattern.compile(".*/(.*$)");
        Matcher matcher = pattern.matcher(imageUrl);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException("Can`t determine file name from url - " + imageUrl);
        }
    }

    private String generateImageName(Long carId, MultipartFile image) {
        return carId + "-" + UUID.randomUUID() + "." + determineExtension(image);
    }

    private String determineExtension(MultipartFile image) {
        String contentTypeValue = image.getContentType();
        if (contentTypeValue == null) {
            String extension = FilenameUtils.getExtension(image.getOriginalFilename());
            if (StringUtils.isBlank(extension)) {
                throw new IllegalArgumentException("Indeterminable file extension. Must be preset either " +
                        "file format in file name (.jpg or .png) or content-type");
            }
            return extension;
        }
        ContentType contentType = getByMimeType(contentTypeValue);
        checkContentType(contentType, IMAGE_JPEG, IMAGE_PNG);
        return convertImageContentTypeToExtension(contentType);
    }

}
