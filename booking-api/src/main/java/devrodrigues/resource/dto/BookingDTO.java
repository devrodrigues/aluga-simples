package devrodrigues.resource.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record BookingDTO (
    @NotNull
    @Positive
    Long vehicleId,
    @NotBlank
    String customerName,
    @FutureOrPresent
    LocalDate startDate,
    @FutureOrPresent
    LocalDate endDate
) {}
