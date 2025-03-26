package com.cungthinh.productservice.utils;

import org.springframework.stereotype.Component;

import com.github.slugify.Slugify;

@Component
public class SlugGenerator {
    private static final Slugify slugify = new Slugify();

    public static String toSlug(String input) {
        return slugify.slugify(input);
    }
}
