//regras de negócio

package devrodrigues.service;

import devrodrigues.model.Booking;
import devrodrigues.model.enums.BookingStatus;
import devrodrigues.repository.BookingRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class BookingService {

    @Inject
    BookingRepository repository;

    public Booking createBooking(Booking booking){
        repository.persist(booking);

        return booking;
    }

    @Transactional
    //cancela ou finaliza a reserva criada
    public Booking changeStatus(Long id, BookingStatus newStatus){
        Booking booking = repository.findById(id);

        if(booking==null){
            throw new NotFoundException("reserva não encontrada");
        }

        if(booking.getStatus() != BookingStatus.CREATED){
            throw new IllegalStateException("reserva já cancelada ou finalizada");
        }

        //altera status;
        return booking;

    }

    public Booking findByid(Long id){
        Booking booking = repository.findById(id);

        if(booking==null){
            throw new NotFoundException("reserva não encontrada");
        }

        return booking;
    }

    public List<Booking> findAll(){
        return repository.listAll();
    }

}
