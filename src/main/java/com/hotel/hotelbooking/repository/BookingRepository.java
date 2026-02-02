package com.hotel.hotelbooking.repository;

import com.hotel.hotelbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
        select count(b) > 0 from Booking b
        where b.room.id = :roomId
          and b.checkIn < :checkOut
          and b.checkOut > :checkIn
        """)
    boolean existsConflict(Long roomId, LocalDate checkIn, LocalDate checkOut);

    List<Booking> findAllByUserId(Long userId);

    List<Booking> findAllByRoomId(Long roomId);

    List<Booking> findByCheckInBetween(LocalDate start, LocalDate end);
}
