package devrodrigues.resource;

import devrodrigues.dto.CreateVehicleRequest;
import devrodrigues.model.Vehicle;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/vehicles")
public class VehicleResource {

    @POST
    @Transactional
    public Response create(CreateVehicleRequest userRequest){

        Vehicle vehicle = new Vehicle(
                userRequest.brand(), userRequest.model(), userRequest.year(), userRequest.engine());

        vehicle.persist();

        return Response.status(Response.Status.CREATED.getStatusCode()).entity(vehicle).build();

    }

    @GET
    public Response getAll(){
    //consultar veículos da frota com seus detalhes e status
        List<Vehicle> list = Vehicle.listAll();

        return Response.ok(list).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long id){
    //consultar veículo pelo seu id, e carTitle -> brand, model + engine

        //veículo pode existir ou não no BD (optional)
        Optional<Vehicle> vehicle = Vehicle.findByIdOptional(id);

        if (vehicle.isPresent()) {
            return Response.ok(vehicle).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /*
    @PATCH()
    @Path("/{id}")
    @Transactional
    public Response updateById(@PathParam("id") Long id, CreateVehicleRequest userRequest){
        //atualizar atributos + status

        Vehicle vehicle = Vehicle.findById(userRequest);

    }
     */

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteById(@PathParam("id") Long id){
        //veículo não pode ser removido se tiver como alugado[

        Vehicle.deleteById(id);
        return Response.noContent().build();

    }

}
