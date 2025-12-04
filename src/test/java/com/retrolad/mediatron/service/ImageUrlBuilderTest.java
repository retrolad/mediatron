package com.retrolad.mediatron.service;

import com.retrolad.mediatron.model.movie.MovieImage;
import com.retrolad.mediatron.model.movie.MovieImageType;
import com.retrolad.mediatron.utils.images.ImageSize;
import com.retrolad.mediatron.utils.images.ImageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ImageUrlBuilderTest {

    private UrlBuilder urlBuilder;
    private ImageUrlBuilder imageUrlBuilder;

    @BeforeEach
    void setUp() {
        urlBuilder = new UrlBuilder();
        // Простой String.join можно не мокать
        imageUrlBuilder = new ImageUrlBuilder("images", urlBuilder);
    }

    private MovieImage mockImage(ImageType type, String url) {
        MovieImage image = mock(MovieImage.class);
        MovieImageType imageType = mock(MovieImageType.class);
        when(imageType.getName()).thenReturn(type.name());
        when(image.getUrl()).thenReturn(url);
        when(image.getType()).thenReturn(imageType);
        return image;
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForTest")
    void buildsBackdropUrl(ImageType imageType, String url, String lang, ImageSize posterSize, String expected) {
        MovieImage image = mockImage(imageType, url);
        String result = imageUrlBuilder.buildImageUrl(image, lang, posterSize);

        assertThat(result).isEqualTo(expected);
    }

    static Stream<Arguments> getArgumentsForTest() {
        return Stream.of(
                Arguments.of(ImageType.BACKDROP, "abc123", null, null, "images/backdrop/abc123.jpg"),
                Arguments.of(ImageType.POSTER, "poster1", "ru", ImageSize.FULL, "images/poster/ru/original/poster1.jpg"),
                Arguments.of(ImageType.POSTER, "poster2", "en", ImageSize.THUMB, "images/poster/en/w220/poster2.jpg"),
                Arguments.of(ImageType.LOGO, "logo69", "de", ImageSize.MEDIUM, "images/logo/de/logo69.jpg")
        );
    }
}
