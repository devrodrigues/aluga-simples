package devrodrigues.dto;

import devrodrigues.model.Vehicle;

public record VehicleResponse (Long id, String brand, String model, String engine,
                               Integer year, String status,
                               String carTitle) {

    //construtor do record
    public VehicleResponse(Vehicle vehicle){
        this(vehicle.getId(), vehicle.getBrand(), vehicle.getModel(), vehicle.getEngine(),
                vehicle.getYear(), vehicle.getStatus().name(),
                vehicle.getBrand() + " " + vehicle.getModel() + " " + vehicle.getEngine());
    }
}
