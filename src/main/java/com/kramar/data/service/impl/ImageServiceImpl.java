package com.kramar.data.service.impl;

import com.kramar.data.dbo.ImageDbo;
import com.kramar.data.exception.BadRequestException;
import com.kramar.data.exception.ErrorReason;
import com.kramar.data.repository.ImageRepository;
import com.kramar.data.service.ImageService;
import com.kramar.data.type.ImageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{

    private static final List<String> VALID_MIME_TYPES = Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE);

    @Autowired
    private ImageRepository imageRepository;

    private void validateFileType(final MultipartFile file) {
        if (file == null || StringUtils.isEmpty(file.getContentType()) ||
                !CollectionUtils.contains(VALID_MIME_TYPES.iterator(), file.getContentType())) {
            throw new BadRequestException(ErrorReason.INVALID_UPLOAD_FILE_TYPE, String.join(",", VALID_MIME_TYPES));
        }
    }

    private byte[] convertFile(final MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            log.error("Unable to convert Multipart file to byte array", e);
            throw new BadRequestException(ErrorReason.UNABLE_TO_CONVERT_IMAGE);
        }
    }

    public ImageDbo createImage(final MultipartFile image) {
        validateFileType(image);
        return new ImageDbo(image.getContentType(), convertFile(image));
    }

    public ImageDbo getImageById(final UUID id) {
        return imageRepository.findOne(id);
    }

    public List<ImageDbo> getImageByIds(final Collection<UUID> ids) {
        return imageRepository.findAll(ids);
    }

    public ImageDbo saveImage(final ImageDbo imageDbo) {
        return imageRepository.save(imageDbo);
    }

    public UUID saveImage(final MultipartFile image, final ImageType imageType) {
        final ImageDbo imageDbo = createImage(image);
        imageDbo.setImageType(imageType);
        return imageRepository.save(imageDbo).getId();
    }

    public void deleteImageById(final UUID id) {
        imageRepository.delete(id);
    }

    public List<UUID> getAllImagesByAdvertId (final UUID id) {
        return null;
//        List<ImageDbo> byAdvert = imageRepository.findByAdvert(advertRepository.findOne(id));
//        return byAdvert.stream().map(ImageDbo::getId).collect(Collectors.toList());
    }

}
