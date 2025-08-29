package devrodrigues.resource;

import devrodrigues.exception.BookingCreatedException;
import devrodrigues.model.Booking;
import devrodrigues.model.enums.BookingStatus;
import devrodrigues.resource.dto.BookingDTO;
import devrodrigues.service.BookingService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingResource {

    @Inject
    BookingService service;

    @POST
    public Response create(@Valid BookingDTO bookingDTO) {

        Booking booking = new Booking(
                bookingDTO.vehicleId(),
                bookingDTO.customerName(),
                bookingDTO.startDate(),
                bookingDTO.endDate()
        );

        boolean created = service.createBooking(booking).isPersistent();

        if (!created) {
            throw new BookingCreatedException("Não foi possível criar a reserva.", Response.Status.CONFLICT);
        }

        return Response.status(Response.Status.CREATED).entity(bookingDTO).build();
    }

    @PATCH
    @Path("{id}")
    public Response updateStatus(@PathParam("id") Long id, BookingStatus status){
        Booking bookingUpdated = service.changeStatus(id, status);

        return Response.ok(bookingUpdated).build();
    }

    @GET
    @Path("{id}")
    public Response getId(@PathParam("id") Long id){
        Booking booking = service.findByid(id);

        if(booking == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().build();
    }

    @GET
    public List<Booking> getAll(){
        return service.findAll();
    }

}
