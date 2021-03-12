package com.vb.utils.validators;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

public class CarDataValidator {

    private static final List<String> allowedContentTypesForImage = Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE);

    private CarDataValidator() {
    }

    public static void validateCarImage(MultipartFile image) {
        if (image.getSize() > 5000000) {
            throw new IllegalArgumentException("Image size must be less then 5 MB");
        } else if (image.getContentType() == null || !isAvailableContentType(image.getContentType())) {
            throw new IllegalArgumentException("Image format must be jpeg or png");
        }
    }

    public static void validateCarImages(List<MultipartFile> images) {
        if (images.size() > 5) {
            throw new IllegalArgumentException("Images count must be less or equals 5");
        }
        images.forEach(CarDataValidator::validateCarImage);
    }

    private static boolean isAvailableContentType(String contentTypeValue) {
        return allowedContentTypesForImage.stream()
                .anyMatch(allowedContentType -> allowedContentType.equals(contentTypeValue));
    }

}
