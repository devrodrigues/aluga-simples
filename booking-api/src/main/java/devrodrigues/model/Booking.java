package devrodrigues.model;

import devrodrigues.exception.InvalidBookingDateException;
import devrodrigues.model.enums.BookingStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "bookings")
public class Booking extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_id")
    private Long vehicleId;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public Booking(){
    }

    public Booking(Long vehicleId, String customerName, LocalDate startDate, LocalDate endDate) {
        this.vehicleId = vehicleId;
        this.customerName = customerName;
        this.startDate = startDate;
        this.endDate = endDate;
        //2 Regra de Negócio - status inicial CREATED
        this.status = BookingStatus.CREATED;
        this.validate();
    }

    private void validate() {
        if (endDate.isBefore(startDate)) {
            throw new InvalidBookingDateException("Data final precisa ser depois da data de início.");
        }
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(vehicleId, booking.vehicleId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vehicleId);
    }
}
