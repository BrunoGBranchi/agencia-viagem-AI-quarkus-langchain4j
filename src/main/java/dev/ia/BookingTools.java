package dev.ia;

import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.awt.print.Book;
import java.util.List;

@ApplicationScoped
public class BookingTools {

    @Inject
    BookingService service;

    @Tool("Lista os pacotes de viagem disponíveis para uma determinada categoria (ex: ADVENTURE, TREASURES).")
    public String listPackagesByCategories(Category category) {
        List<Booking> bookings = service.getBookingsByCategory(category);
        if (bookings.isEmpty()){
            return "Nenhum pacote para a categoria " + category + " encontrado.";
        }
        return "Pacotes encontrados para a categoria '" + category + "': " + bookings.stream().map(Booking::destination).toList().toString();
    }

    @Tool("Obtém a lista com todas as reservas cadastradas.")
    public Iterable<Booking> getAllBookings(){
        return service.getAllBookings();
    }

    @Tool("Obtém os detalhes completos de uma reserva com base em seu numero de identificação (bookingId).")
    public String getBookingDetails(long bookingId){
        return service.getBookingDetails(bookingId).map(Booking::toString).orElse("Reserva com ID " + bookingId + " não encontrada.");
    }

    @Tool("""
            Cancela uma reserva existente.
            Para confirmar o cancelamento, é necessário fornecer ID da reserva (bookingId). O usuario deve estar autenticado.
            """)
    public String cancelBooking(long bookingId){
        return service.cancelBooking(bookingId).map(
                booking -> "Reserva " + bookingId + " cancelada com sucesso. Status atual: " + booking.status()
                ).orElse("Não foi possivel cancelar a reserva. Verifique se o ID da reserva e se você tem permissão.");

    }
}
