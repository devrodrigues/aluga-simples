//regras de negócio

package devrodrigues.service;

import devrodrigues.exception.BookingWithConflictException;
import devrodrigues.exception.VehicleNotAvailableException;
import devrodrigues.model.Booking;
import devrodrigues.model.enums.BookingStatus;
import devrodrigues.repository.BookingRepository;
import devrodrigues.repository.VehicleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookingService {

    @Inject //somente panache repository
    BookingRepository bookingRepository;

    @Inject
    @RestClient //cliente HTTP
    VehicleRepository vehicleRepository;

    @Transactional
    public Booking createBooking(Booking booking){
        //verificando conflito e indisponibilidade
        Optional<Booking> conflictBooking = this.bookingRepository
                .findConflictingBooking(booking.getVehicleId(), booking.getStartDate(), booking.getEndDate());

        if (conflictBooking.isPresent()) {
            throw new BookingWithConflictException("Veículo já alugado.");
        }

        VehicleRepository.Vehicle vehicle = vehicleRepository.findVehicleById(booking.getVehicleId());

        if (!vehicle.status().equals("AVAILABLE")) {
            throw new VehicleNotAvailableException("Veículo não está disponível no momento.");
        }

        //senão, cria a reserva
        this.bookingRepository.persist(booking);

        return booking;
    }

    @Transactional
    //cancela ou finaliza a reserva criada
    public Booking changeStatus(Long id, BookingStatus newStatus){
        Booking booking = bookingRepository.findById(id);

        if(booking==null){
            throw new NotFoundException("reserva não encontrada");
        }

        if(booking.getStatus() != BookingStatus.CREATED){
            throw new IllegalStateException("reserva já cancelada ou finalizada");
        }

        booking.changeStatus(newStatus);

        return booking;

    }

    public Booking findByid(Long id){
        Booking booking = bookingRepository.findById(id);

        if(booking==null){
            throw new NotFoundException("reserva não encontrada");
        }

        return booking;
    }

    public List<Booking> findAll(){
        return bookingRepository.listAll();
    }

}
