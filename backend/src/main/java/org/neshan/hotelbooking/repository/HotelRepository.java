package org.neshan.hotelbooking.repository;

import org.neshan.hotelbooking.model.dto.HotelDTO;
import org.neshan.hotelbooking.model.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query(value = """
            WITH available_rooms AS (
                SELECT r.id AS room_id,
                       r.hotel_id,
                       r.price_per_night
                FROM rooms r
                WHERE NOT EXISTS (
                      SELECT 1
                      FROM bookings b
                      WHERE (b.status = 'BOOKED' OR (b.status = 'PENDING' AND b.pending_expires_at > now()))
                        AND b.room_id = r.id
                        AND (b.check_in_date < :dateTo AND b.check_out_date > :dateFrom)
                  )
            ),
            cheapest_rooms AS (
                SELECT DISTINCT ON (ar.hotel_id)
                       ar.hotel_id,
                       ar.room_id,
                       ar.price_per_night
                FROM available_rooms ar
                ORDER BY ar.hotel_id, ar.price_per_night
            )
            SELECT h.id, h.name, h.city, h.address, 'http://localhost/images/' || h.image_path as image_path, h.stars, h.rate, h.votes, h.features,
                   (cr.price_per_night * EXTRACT(DAY FROM (CAST(:dateTo AS TIMESTAMP) - CAST(:dateFrom AS TIMESTAMP)))) AS price,
                    CAST(EXTRACT(DAY FROM (CAST(:dateTo AS TIMESTAMP) - CAST(:dateFrom AS TIMESTAMP))) AS INTEGER) AS night_count
            FROM hotels h
            JOIN cheapest_rooms cr ON h.id = cr.hotel_id
            WHERE h.city = :city
              AND (:stars IS NULL OR h.stars = :stars)
              AND (:priceFrom IS NULL OR (cr.price_per_night * EXTRACT(DAY FROM (CAST(:dateTo AS TIMESTAMP) - CAST(:dateFrom AS TIMESTAMP)))) >= :priceFrom)
              AND (:priceTo IS NULL OR (cr.price_per_night * EXTRACT(DAY FROM (CAST(:dateTo AS TIMESTAMP) - CAST(:dateFrom AS TIMESTAMP)))) <= :priceTo);
            """, nativeQuery = true)
    List<HotelDTO> getHotels(@Param("city") String city,
                             @Param("dateFrom") LocalDateTime dateFrom,
                             @Param("dateTo") LocalDateTime dateTo,
                             @Param("stars") Integer stars,
                             @Param("priceFrom") Double priceFrom,
                             @Param("priceTo") Double priceTo);
}
