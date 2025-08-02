package org.neshan.hotelbooking.model.factory;

import org.neshan.hotelbooking.model.dto.RoomResponse;
import org.neshan.hotelbooking.model.entity.Hotel;
import org.neshan.hotelbooking.model.entity.Room;

import java.util.Comparator;
import java.util.List;

public class RoomResponseFactory {
    public static final String BASE_PATH = "http://localhost:8081/images/";

    public static RoomResponse fromEntities(List<Room> rooms, Hotel hotel, int nightsCount) {
        List<RoomResponse.RoomDTO> roomDTOS = rooms.stream()
                .map(room -> fromEntity(room, nightsCount))
                .sorted(Comparator.comparingDouble(RoomResponse.RoomDTO::getPrice))
                .toList();

        return RoomResponse.builder()
                .hotelName(hotel.getName())
                .stars(hotel.getStars())
                .address(hotel.getAddress())
                .imagePath(BASE_PATH + hotel.getImagePath())
                .description(hotel.getDescription())
                .features(hotel.getFeatures())
                .rooms(roomDTOS)
                .build();
    }

    private static RoomResponse.RoomDTO fromEntity(Room room, int nightsCount) {
        return RoomResponse.RoomDTO.builder()
                .id(room.getId())
                .title(room.getTitle())
                .capacity(room.getCapacity())
                .nightCount(nightsCount)
                .price(room.getPricePerNight() * nightsCount)
                .build();
    }
}
