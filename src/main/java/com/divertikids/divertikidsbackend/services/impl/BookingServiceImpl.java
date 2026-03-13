package com.divertikids.divertikidsbackend.services.impl;

import com.divertikids.divertikidsbackend.dtos.BookingDTO;
import com.divertikids.divertikidsbackend.persistence.entities.Booking;
import com.divertikids.divertikidsbackend.persistence.repositories.BookingRepository;
import com.divertikids.divertikidsbackend.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setName(bookingDTO.getName());
        booking.setEmail(bookingDTO.getEmail());
        booking.setEventDate(bookingDTO.getEventDate());
        booking.setStartTime(bookingDTO.getStartTime());
        booking.setEndTime(bookingDTO.getEndTime());
        booking.setBudget(bookingDTO.getBudget());

        return bookingRepository.save(booking);
    }
}
