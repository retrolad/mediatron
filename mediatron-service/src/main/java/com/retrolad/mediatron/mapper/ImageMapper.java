package com.retrolad.mediatron.mapper;

import com.retrolad.mediatron.dto.ImageDto;
import com.retrolad.mediatron.utils.images.ImageSize;
import com.retrolad.mediatron.utils.images.ImageType;
import com.retrolad.mediatron.model.movie.MovieImage;
import com.retrolad.mediatron.service.ImageUrlBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ImageMapper {

    private final ImageUrlBuilder urlBuilder;

    public ImageDto toDto(Set<MovieImage> images, String lang, ImageSize posterSize) {
        String backdropPath = "";
        String posterPath = "";
        String logoPath = "";

        for (var image : images) {
            ImageType type = ImageType.valueOf(image.getType().getName().toUpperCase());

            // Пока используем чужое облако с изображениями
//            String url = urlBuilder.buildImageUrl(image, lang, posterSize);
            String url = image.getUrl();

            switch (type) {
                case POSTER -> {
                    if (!image.getLangCode().equals(lang)) continue;
                    posterPath = url;
                }
                case BACKDROP -> backdropPath = url;
                case LOGO -> {
                    if (!image.getLangCode().equals(lang)) continue;
                    logoPath = url;
                }
            }
        }

        return new ImageDto(backdropPath, posterPath, logoPath);
    }
}
