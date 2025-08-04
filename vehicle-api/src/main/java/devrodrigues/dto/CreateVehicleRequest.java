package devrodrigues.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateVehicleRequest(
        @NotBlank String brand,
        @NotBlank String model,
        @NotNull Integer year,
        @NotBlank String engine) {
}
