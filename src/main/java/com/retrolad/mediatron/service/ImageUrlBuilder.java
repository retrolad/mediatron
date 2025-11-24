package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.ImageSize;
import com.retrolad.mediatron.model.movie.MovieImage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Собирает полный путь изображений для разных типов и масштабов
 */
@Service
public class ImageUrlBuilder {

    @Value("${app.images.base-dir}")
    private String baseDir;

    public String buildImageUrl(MovieImage image, String lang, ImageSize posterSize) {
        String type = image.getType().getName();
        String url = "";
        String size = "";

        switch (type) {
            case "backdrop" -> url = baseDir + "/backdrop";
            case "poster" -> {
                if (posterSize == ImageSize.THUMB) {
                    size = "/w220";
                } else if (posterSize == ImageSize.MEDIUM) {
                    size = "/w300";
                } else if (posterSize == ImageSize.FULL) {
                    size = "/original";
                }

                url = baseDir + "/poster/" + lang + size;
            }
            case "logo" -> url = baseDir + "/logo/" + lang;
        }

        url += "/" + image.getUrl() + ".jpg";
        return url;
    }
}
