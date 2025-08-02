package org.neshan.hotelbooking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.neshan.hotelbooking.model.dto.HotelRequest;
import org.neshan.hotelbooking.model.dto.HotelDTO;
import org.neshan.hotelbooking.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Hotels")
@CrossOrigin(origins = "http://localhost:8081")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class HotelController {
    HotelService hotelService;

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelDTO>> getHotels(@Valid @ModelAttribute HotelRequest request) {
        List<HotelDTO> hotels = hotelService.getHotels(request);
        return ResponseEntity.ok(hotels);
    }
}
