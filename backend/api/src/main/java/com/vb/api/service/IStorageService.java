package com.vb.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IStorageService {

    void saveCarImages(Long carId, List<MultipartFile> images, List<String> imagesUrls);

    void saveCarImage(Long carId, MultipartFile image, List<String> imagesUrls);

    boolean deleteImage(String imageUrl);

}
