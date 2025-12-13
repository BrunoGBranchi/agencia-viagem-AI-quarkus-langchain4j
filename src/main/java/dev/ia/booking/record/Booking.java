package dev.ia.booking.record;

import dev.ia.booking.enums.BookingStatus;
import dev.ia.booking.enums.Category;

import java.time.LocalDate;

public record Booking(
        Long id,
        String customerName,
        String destination,
        LocalDate startDate,
        LocalDate endDate,
        BookingStatus status,
        Category category
) {
}
