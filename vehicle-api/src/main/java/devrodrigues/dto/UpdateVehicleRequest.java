package devrodrigues.dto;

import devrodrigues.model.enums.VehicleStatus;

public record UpdateVehicleRequest (String brand, String model, String engine,
                                    Integer year, VehicleStatus status){
}
