package com.retrolad.mediatron.model.movie;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movie_image")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class MovieImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    @ToString.Exclude
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    @ToString.Exclude
    private MovieImageType type;

    @Column(nullable = false)
    private String url;

    @Column(length = 2)
    private String langCode;

    private Boolean isDefault = false;
}
