package devrodrigues.resource;

import devrodrigues.model.Booking;
import devrodrigues.model.enums.BookingStatus;
import devrodrigues.service.BookingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/bookings")
public class BookingResource {

    @Inject
    BookingService service;

    @POST
    public Response create(Booking booking){
        Booking bookingCreated = service.createBooking(booking);

        return Response.status(Response.Status.CREATED).entity(bookingCreated).build();
    }

    @PATCH
    @Path("{id}")
    public Response updateStatus(@PathParam("id") Long id, BookingStatus status){
        Booking bookingUpdated = service.changeStatus(id, status);

        return Response.ok(bookingUpdated).build();
    }

    @GET
    @Path("{id}")
    public Booking getId(@PathParam("id") Long id){
        return service.findByid(id);
    }

    @GET
    public List<Booking> getAll(){
        return service.findAll();
    }

}
