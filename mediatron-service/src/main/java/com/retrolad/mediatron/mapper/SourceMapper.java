package com.retrolad.mediatron.mapper;

import com.retrolad.mediatron.model.movie.MovieSourceData;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Component
public class SourceMapper {

    public <T, E extends MovieSourceData<T>> Map<String, T> toDto(Set<E> sourceData) {
        return sourceData.stream()
                .collect(
                        TreeMap::new,
                        (map, data) -> map.put(data.getSource().getName(), data.getData()),
                        TreeMap::putAll);
    }
}
