package devrodrigues.repository;

//interface para que booking-api se comunique com vehicle-api
import devrodrigues.exception.VehicleNotFoundException;
import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "vehicle-api") //url base - application.properties
public interface VehicleRepository {

    @GET
    @Path("/api/vehicles/{vehicleId}")
    Vehicle findVehicleById(@PathParam("vehicleId") Long id);

    @ClientExceptionMapper
    static VehicleNotFoundException handler(Response response) {
        if (response.getStatus() == 404) {
            return new VehicleNotFoundException("Veículo não encontrado");
        }
        return null;
    }

    record Vehicle(String status) {}
}