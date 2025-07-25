package org.neshan.hotelbooking.repository;

import org.neshan.hotelbooking.model.entity.Booking;
import org.neshan.hotelbooking.model.entity.User;
import org.neshan.hotelbooking.model.enumeration.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByStatusAndPendingExpiresAtBefore(BookingStatus bookingStatus, LocalDateTime now);
    Optional<Booking> findByPaymentCode(long paymentCode);
    Optional<Booking> findByAuthority(String authority);
    List<Booking> findAllByUserAndStatusAndCheckInDateAfter(User user, BookingStatus status, LocalDateTime now);
    List<Booking> findAllByUserAndStatusAndCheckInDateBefore(User user, BookingStatus status, LocalDateTime now);
}
