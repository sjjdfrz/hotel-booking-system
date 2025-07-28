package org.neshan.hotelbooking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.neshan.hotelbooking.model.dto.HotelDTO;
import org.neshan.hotelbooking.model.dto.HotelRequest;
import org.neshan.hotelbooking.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HotelService {
    HotelRepository hotelRepository;

    public List<HotelDTO> getHotels(HotelRequest request) {
        List<HotelDTO> hotelDTOs = hotelRepository.getHotels(
                request.getCity(),
                request.getDateFrom(),
                request.getDateTo(),
                request.getStars(),
                request.getPriceFrom(),
                request.getPriceTo());

        switch (request.getSort().toLowerCase()) {
            case "rate":
                hotelDTOs.sort(Comparator.comparing(HotelDTO::getRate).reversed());
                break;
            case "price", "price,asc":
                hotelDTOs.sort(Comparator.comparing(HotelDTO::getPrice));
                break;
            case "price,desc":
                hotelDTOs.sort(Comparator.comparing(HotelDTO::getPrice).reversed());
                break;
            default:
                break;
        }

        return hotelDTOs;
    }
}
