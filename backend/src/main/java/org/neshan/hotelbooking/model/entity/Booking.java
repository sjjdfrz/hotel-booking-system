package org.neshan.hotelbooking.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.neshan.hotelbooking.model.enumeration.BookingStatus;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "bookings", schema = "public")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    long paymentCode;

    double price;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(nullable = false)
    LocalDateTime checkInDate;

    @Column(nullable = false)
    LocalDateTime checkOutDate;

    LocalDateTime pendingExpiresAt;

    @Enumerated(EnumType.STRING)
    BookingStatus status;

    String authority;
    Long refId;
}
