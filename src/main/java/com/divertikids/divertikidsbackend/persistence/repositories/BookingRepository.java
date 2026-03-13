package com.divertikids.divertikidsbackend.persistence.repositories;

import com.divertikids.divertikidsbackend.persistence.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
