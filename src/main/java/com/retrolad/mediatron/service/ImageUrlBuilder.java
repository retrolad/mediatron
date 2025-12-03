package com.retrolad.mediatron.service;

import com.retrolad.mediatron.utils.images.ImageSize;
import com.retrolad.mediatron.utils.images.ImageType;
import com.retrolad.mediatron.model.movie.MovieImage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Собирает полный путь изображений для разных типов и масштабов
 */
@Service
@RequiredArgsConstructor
public class ImageUrlBuilder {

    private final UrlBuilder urlBuilder;

    private final String SIZE_W220 = "w220";
    private final String SIZE_W300 = "w300";
    private final String SIZE_ORIGINAL = "original";
    private final String imageExt = ".jpg";

    @Value("${app.images.base-dir}")
    private String baseDir;

    public String buildImageUrl(MovieImage image, String lang, ImageSize posterSize) {
        ImageType type = ImageType.valueOf(image.getType().getName().toUpperCase());
        String url = "";
        String size = "";

        switch (type) {
            case BACKDROP -> url = urlBuilder.pathFrom(baseDir, type.name().toLowerCase());
            case POSTER -> {
                if (posterSize == ImageSize.THUMB) {
                    size = SIZE_W220;
                } else if (posterSize == ImageSize.MEDIUM) {
                    size = SIZE_W300;
                } else if (posterSize == ImageSize.FULL) {
                    size = SIZE_ORIGINAL;
                }

                url = urlBuilder.pathFrom(baseDir, type.name().toLowerCase(), lang, size);
            }
            case LOGO -> url = urlBuilder.pathFrom(baseDir, type.name().toLowerCase(), lang);
        }

        return urlBuilder.pathFrom(url, image.getUrl() + imageExt);
    }
}
