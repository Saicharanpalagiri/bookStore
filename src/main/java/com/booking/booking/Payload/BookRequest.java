package com.booking.booking.Payload;

import lombok.Data;

@Data
public class BookRequest {
    private String username;
    private String title;
    private String author;
    private String genre;
}
