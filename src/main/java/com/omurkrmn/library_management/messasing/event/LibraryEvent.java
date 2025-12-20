package com.omurkrmn.library_management.messasing.event;

import java.time.LocalDateTime;

public record LibraryEvent(

        String type,
        String message,
        LocalDateTime time
) {
}
