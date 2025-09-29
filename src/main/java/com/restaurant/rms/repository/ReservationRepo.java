package com.restaurant.rms.repository;

import com.restaurant.rms.models.Reservation;
import com.restaurant.rms.models.ReservationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepo extends JpaRepository<Reservation, ReservationId> {
}
