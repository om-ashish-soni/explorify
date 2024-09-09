package com.learn.explorify.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("event")
public record Event (
        @Id String id,
        String name
) {
}
