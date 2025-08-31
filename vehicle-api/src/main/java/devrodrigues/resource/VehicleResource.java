package devrodrigues.resource;

import devrodrigues.dto.CreateVehicleRequest;
import devrodrigues.dto.UpdateVehicleRequest;
import devrodrigues.dto.VehicleResponse;
import devrodrigues.model.Vehicle;
import devrodrigues.model.enums.VehicleStatus;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/vehicles")
public class VehicleResource {

    @POST
    @RolesAllowed({"ADMIN", "FUNCIONARIO"})
    @Transactional
    public Response create(@Valid CreateVehicleRequest userRequest){

        Vehicle vehicle = new Vehicle(
                userRequest.brand(), userRequest.model(), userRequest.year(), userRequest.engine());

        vehicle.persist();

        //usando dto para não expor a entidade Vehicle
        VehicleResponse vehicleResponse = new VehicleResponse(vehicle);

        return Response.status(Response.Status.CREATED.getStatusCode()).entity(vehicleResponse).build();

    }

    @GET
    @RolesAllowed({"ADMIN", "FUNCIONARIO", "USUARIO"})
    public Response getAll(){
    //consultar veículos da frota com seus detalhes e status

        List<VehicleResponse> list = Vehicle.listAll().stream()
                .map(v -> new VehicleResponse((Vehicle) v))
                .toList();


        return Response.ok(list).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long id){
    //consultar veículo pelo seu id, e carTitle -> brand, model + engine

        //veículo pode existir ou não no BD (optional)
        Optional<Vehicle> vehicle = Vehicle.findByIdOptional(id);

        if (vehicle.isPresent()) {
            return Response.ok(new VehicleResponse((vehicle.get()))).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @PATCH()
    @Path("/{id}")
    @Transactional
    public Response updateById(@PathParam("id") Long id, UpdateVehicleRequest userRequest){
        //atualizar atributos + status

        Vehicle vehicle = Vehicle.findById(id);

        if(vehicle!=null){
            try{
                vehicle.updateFromRequest(userRequest);
                return Response.ok(new VehicleResponse(vehicle)).build();
            } catch(IllegalStateException e) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(Map.of(
                                "error", "CONFLICT",
                                "message", e.getMessage()
                        )).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    @Transactional
    public Response deleteById(@PathParam("id") Long id) {
        Vehicle vehicle = Vehicle.findById(id);

        if (vehicle == null) {
            // Retorna 404 se não encontrado
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of(
                            "error", "NOT_FOUND",
                            "message", "Veículo não encontrado."
                    )).build();
        }

        if (vehicle.getStatus() == VehicleStatus.RENTED) {
            // Retorna 409 se veículo estiver alugado
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of(
                            "error", "CONFLICT",
                            "message", "O veículo não pode ser deletado pois está alugado no momento."
                    )).build();
        }

        // Se chegou aqui, pode deletar
        vehicle.delete();
        return Response.noContent().build(); // 204 No Content
    }

}
