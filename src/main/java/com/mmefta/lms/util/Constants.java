package com.mmefta.lms.util;

import lombok.Getter;

@Getter
public class Constants {
    public static final int MAX_LENDING_DAYS = 14;
    public static final int MAX_BOOKS_ALLOWED = 5;

    public enum BookFormat {
        HARDCOVER,
        PAPERBACK,
        AUDIO_BOOK,
        EBOOK,
        NEWSPAPER
    }

    public enum BookStatus {
        AVAILABLE,
        RESERVED,
        LOANED,
        LOST
    }

    public enum ReservationStatus {
        PENDING,
        CANCELED,
        NONE
    }

    public enum AccountStatus {
        ACTIVE,
        CLOSED,
        CANCELED,
        BLACKLISTED,
        NONE
    }
}
