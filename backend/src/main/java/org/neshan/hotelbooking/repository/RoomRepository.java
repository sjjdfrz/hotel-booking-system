package org.neshan.hotelbooking.repository;

import org.neshan.hotelbooking.model.entity.Room;
import org.neshan.hotelbooking.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("""
            SELECT r
            FROM Room r
            WHERE r.hotel.id = :hotelId
              AND r.id NOT IN (
                  SELECT b.room.id
                  FROM Booking b
                  WHERE ((b.status = 'BOOKED') OR (b.status = 'PENDING' AND b.pendingExpiresAt > CURRENT_TIMESTAMP AND (:user IS NULL OR b.user != :user)))
                    AND (b.checkInDate < :dateTo AND b.checkOutDate > :dateFrom)
              )
            """)
    List<Room> getAvailableRoomsByHotel(@Param("hotelId") Long hotelId,
                                        @Param("dateFrom") LocalDateTime dateFrom,
                                        @Param("dateTo") LocalDateTime dateTo,
                                        @Param("user") User user);

    @Query(value = """
            SELECT r
            FROM Room r
            WHERE r.id = :roomId
             AND r.id NOT IN (
                  SELECT b.room.id
                  FROM Booking b
                  WHERE ((b.status = 'BOOKED') OR (b.status = 'PENDING' AND b.pendingExpiresAt > CURRENT_TIMESTAMP AND b.user != :user))
                    AND (b.checkInDate < :dateTo AND b.checkOutDate > :dateFrom)
              )
            """)
    Optional<Room> getAvailableRoomById(@Param("roomId") Long roomId,
                                        @Param("dateFrom") LocalDateTime dateFrom,
                                        @Param("dateTo") LocalDateTime dateTo,
                                        @Param("user") User user);
}
