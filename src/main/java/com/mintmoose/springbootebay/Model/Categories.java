package com.mintmoose.springbootebay.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Categories {
    CLOTHING,
    SHOES,
    ELECTRONICS,
    BOOKS_MOVIES_MUSIC,
    HOBBY_DIY,
    HOUSEHOLD,
    HEALTH_BEAUTY,
    SPORTS_OUTDOORS,
    FOOD_GROCERY;

    @JsonValue
    public String getValue() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static Categories fromValue(String value) {
        return valueOf(value.toUpperCase());
    }
}
