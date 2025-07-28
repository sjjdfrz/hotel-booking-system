package org.neshan.hotelbooking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.neshan.hotelbooking.model.dto.ApiResponse;
import org.neshan.hotelbooking.model.dto.BookingDTO;
import org.neshan.hotelbooking.model.dto.BookingRequest;
import org.neshan.hotelbooking.model.entity.User;
import org.neshan.hotelbooking.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Bookings")
@CrossOrigin(origins = "http://localhost")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {
    BookingService bookingService;

    @PostMapping("/bookings")
    public ResponseEntity<ApiResponse<BookingDTO>> bookRoom(@Valid @RequestBody BookingRequest request,
                                                            @AuthenticationPrincipal User user) {
        ApiResponse<BookingDTO> response = bookingService.bookRoom(request, user);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<ApiResponse<BookingDTO>> getBooking(@PathVariable("id") Long bookingId) {
        ApiResponse<BookingDTO> response = bookingService.getBooking(bookingId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
