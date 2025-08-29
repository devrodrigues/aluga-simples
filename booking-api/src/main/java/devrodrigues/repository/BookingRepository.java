//acesso ao BD

package devrodrigues.repository;

import devrodrigues.model.Booking;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.Optional;

@ApplicationScoped
public class BookingRepository implements PanacheRepository<Booking> {

    //para verificar conflito de reserva
    public Optional<Booking> findConflictingBooking(Long vehicleId, LocalDate startDate, LocalDate endDate) {
        return find("vehicleId = :vehicleId AND startDate < :endDate AND endDate > :startDate",
                Parameters.with("vehicleId", vehicleId)
                        .and("startDate", startDate)
                        .and("endDate", endDate)).firstResultOptional();
    }

}
