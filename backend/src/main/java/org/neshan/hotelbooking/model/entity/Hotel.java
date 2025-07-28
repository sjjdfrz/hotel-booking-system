package org.neshan.hotelbooking.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "hotels", schema = "public")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    String name;
    String description;
    String city;
    String address;
    String imagePath;
    int stars;
    int rate;
    int votes;
    List<String> features;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    List<Room> rooms;
}
