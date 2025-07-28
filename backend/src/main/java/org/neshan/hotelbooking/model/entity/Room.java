package org.neshan.hotelbooking.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "rooms", schema = "public")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    Hotel hotel;

    String title;
    int capacity;
    Double pricePerNight;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    List<Booking> bookings = new ArrayList<>();
}