package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.MoviePersonDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;

/**
 * Сортирует актеров по их релевантности
 */
@Component
public class MoviePersonOrderComparator implements Comparator<MoviePersonDto> {

    @Override
    public int compare(MoviePersonDto o1, MoviePersonDto o2) {
        Short order1 = o1.order();
        Short order2 = o2.order();
        if (order1 == null && order2 == null) {
            // TODO сделать сортировку не актеров по роли
            return 0;
        } else if (order1 == null) {
            return 1;
        } else if (order2 == null) {
            return -1;
        } else {
            return Short.compare(order1, order2);
        }
    }
}
