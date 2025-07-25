package org.neshan.hotelbooking.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HotelDTO {
    long id;
    String name;
    String city;
    String address;
    String imagePath;
    int stars;
    int rate;
    int votes;
    String[] features;
    Double price;
    int nightCount;
}
