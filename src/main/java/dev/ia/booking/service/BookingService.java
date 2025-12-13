package dev.ia.booking.service;

import dev.ia.booking.enums.BookingStatus;
import dev.ia.booking.enums.Category;
import dev.ia.booking.record.Booking;
import dev.ia.configurations.security.SecurityContext;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class BookingService {

    private final Map<Long, Booking> bookings = new HashMap<>();

    public BookingService() {
        bookings.put(12345L, new Booking(12345L,"Jhon Doe", "Tesouros do Egito",
                LocalDate.now().plusMonths(2L),
                LocalDate.now().plusMonths(2).plusDays(10),
                BookingStatus.CONFIRMED, Category.TREASURES));
        bookings.put(67890L, new Booking(67890L,"Jame Smith", "Aventura Amazônia",
                LocalDate.now().plusMonths(3),
                LocalDate.now().plusMonths(3).plusDays(7),
                BookingStatus.CONFIRMED, Category.ADVENTURE));
        bookings.put(98765L, new Booking(98765L,"Peter Jones", "Trilha Inca",
                LocalDate.now().plusMonths(4),
                LocalDate.now().plusMonths(4).plusDays(8),
                BookingStatus.CONFIRMED, Category.ADVENTURE));
    }

    public List<Booking> getBookingsByCategory(Category category) {
        return bookings.values().stream().filter(booking -> booking.category().equals(category)).toList();
    }

    public Iterable<Booking> getAllBookings() {
        return bookings.values();
    }

    public Optional<Booking> getBookingDetails(Long bookingId) {
        return Optional.ofNullable(bookings.get(bookingId));
    }

    public Optional<Booking> cancelBooking(long bookingId) {
        String currentUserName = SecurityContext.getCurrentUser();
        if (bookings.containsKey(bookingId)){
            Booking booking = bookings.get(bookingId);
            if (booking.customerName().equals(currentUserName)){
                    Booking cancelledBooking = new Booking(
                            bookingId,
                            booking.customerName(),
                            booking.destination(),
                            booking.startDate(),
                            booking.endDate(),
                            BookingStatus.CANCELLED,
                            booking.category());
                    this.bookings.replace(bookingId, cancelledBooking);
                    return Optional.of(cancelledBooking);
            }

        }
        return Optional.empty();
    }

}
