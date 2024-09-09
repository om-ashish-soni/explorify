package com.learn.explorify.model;

import jakarta.annotation.Nullable;

public record Product(
        int id,
        String name,
        int price
) {
}
