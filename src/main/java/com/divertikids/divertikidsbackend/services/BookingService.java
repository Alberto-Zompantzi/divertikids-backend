package com.divertikids.divertikidsbackend.services;

import com.divertikids.divertikidsbackend.dtos.BookingDTO;
import com.divertikids.divertikidsbackend.persistence.entities.Booking;

public interface BookingService {
    Booking createBooking(BookingDTO bookingDTO);
}
