package devrodrigues.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class BookingCreatedException extends WebApplicationException {
    public BookingCreatedException(String message, Response.Status status) {
        super(Response.status(status).entity(message).build());
    }
}