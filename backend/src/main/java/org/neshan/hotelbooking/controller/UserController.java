package org.neshan.hotelbooking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.neshan.hotelbooking.model.dto.ApiResponse;
import org.neshan.hotelbooking.model.dto.BookingDTO;
import org.neshan.hotelbooking.model.entity.User;
import org.neshan.hotelbooking.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Users")
@CrossOrigin(origins = "http://localhost")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    BookingService bookingService;

    @GetMapping("/users/me/current-bookings")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getCurrentUserBookings(@AuthenticationPrincipal User user) {
        ApiResponse<List<BookingDTO>> response = bookingService.getCurrentUserBookings(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/me/past-bookings")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getPastUserBookings(@AuthenticationPrincipal User user) {
        ApiResponse<List<BookingDTO>> response = bookingService.getPastUserBookings(user);
        return ResponseEntity.ok(response);
    }
}
