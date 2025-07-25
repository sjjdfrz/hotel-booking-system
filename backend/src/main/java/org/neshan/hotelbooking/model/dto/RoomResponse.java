package org.neshan.hotelbooking.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomResponse {
    String hotelName;
    int stars;
    String address;
    String imagePath;
    String description;
    List<String> features;
    List<RoomDTO> rooms;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class RoomDTO {
        long id;
        String title;
        int capacity;
        Double price;
        int nightCount;
    }
}
