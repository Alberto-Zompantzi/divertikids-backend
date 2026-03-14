package com.divertikids.divertikidsbackend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingDTO {

    @NotBlank(message = "El nombre es obligatorio.")
    private String name;

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "Formato de email inválido.")
    private String email;

    @NotNull(message = "La fecha del evento es obligatoria.")
    @FutureOrPresent(message = "La fecha del evento debe ser hoy o una fecha futura.")
    private LocalDate eventDate;

    @NotNull(message = "La hora de inicio es obligatoria.")
    @JsonFormat(pattern = "HH:mm[:ss]")
    private LocalTime startTime;

    @NotNull(message = "La hora de finalización es obligatoria.")
    @JsonFormat(pattern = "HH:mm[:ss]")
    private LocalTime endTime;

    @NotNull(message = "El presupuesto es obligatorio.")
    @PositiveOrZero(message = "El presupuesto no puede ser negativo.")
    private BigDecimal budget;


}
