package com.retrolad.mediatron.mapper;

import com.retrolad.mediatron.model.ProductionCountry;

public class CountryMapper {

    public static String toDto(ProductionCountry country, String lang) {
        return country.getTranslations().get(lang);
    }
}
