package com.retrolad.mediatron.service;

import com.retrolad.mediatron.model.movie.MovieImage;
import com.retrolad.mediatron.model.movie.MovieImageType;
import com.retrolad.mediatron.utils.images.ImageSize;
import com.retrolad.mediatron.utils.images.ImageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    void buildsBackdropUrl() {
        MovieImage image = mockImage(ImageType.BACKDROP, "abc");
        String result = imageUrlBuilder.buildImageUrl(image, null, null);

        assertThat(result).isEqualTo("images/backdrop/abc.jpg");
    }

    @Test
    void buildsFullSizedPosterUrl() {
        MovieImage image = mockImage(ImageType.POSTER, "abc");
        String result = imageUrlBuilder.buildImageUrl(image, "ru", ImageSize.FULL);

        assertThat(result).isEqualTo("images/poster/ru/original/abc.jpg");
    }

    @Test
    void buildsThumbPosterUrl() {
        MovieImage image = mockImage(ImageType.POSTER, "poster12");
        String result = imageUrlBuilder.buildImageUrl(image, "en", ImageSize.THUMB);

        assertThat(result).isEqualTo("images/poster/en/w220/poster12.jpg");
    }

    @Test
    void buildsLogoUrl() {
        MovieImage image = mockImage(ImageType.LOGO, "logo69");
        String result = imageUrlBuilder.buildImageUrl(image, "de", ImageSize.THUMB);

        assertThat(result).isEqualTo("images/logo/de/logo69.jpg");
    }
}
