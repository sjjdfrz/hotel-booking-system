package org.neshan.hotelbooking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.neshan.hotelbooking.model.dto.ApiResponse;
import org.neshan.hotelbooking.model.dto.RoomResponse;
import org.neshan.hotelbooking.model.dto.RoomRequest;
import org.neshan.hotelbooking.model.entity.User;
import org.neshan.hotelbooking.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
@Tag(name = "Rooms")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomController {
    RoomService roomService;

    @GetMapping("/rooms")
    public ResponseEntity<ApiResponse<RoomResponse>> getRoomsOfHotel(@Valid @ModelAttribute RoomRequest request,
                                                                     @AuthenticationPrincipal User user) {
        ApiResponse<RoomResponse> response = roomService.getRooms(request, user);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
