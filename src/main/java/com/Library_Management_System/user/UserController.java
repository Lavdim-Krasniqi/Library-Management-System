package com.Library_Management_System.user;

import com.Library_Management_System.user.dto.LoginDto;
import com.Library_Management_System.user.dto.UserDto;
import com.Library_Management_System.user.entity.User;
import com.Library_Management_System.user.service.UserService;
import com.mongodb.client.result.UpdateResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    UserService service;

    @PostMapping("/addUser")
    @Operation(
            summary = "Add a new user",
            description = "This endpoint adds a new user to the system. Anyone can access this endpoint.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User added successfully")
            }
    )
    public User addUser(@RequestBody UserDto dto) {
        return service.addUser(dto);
    }

    @PreAuthorize("@methodAuthorization.hasRole('ROLE_ADMIN')")
    @PostMapping("/addAdmin")
    @Operation(
            summary = "Add a new admin",
            description = "This endpoint allows administrators to add a new admin user to the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Admin added successfully"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - Only admins can access this endpoint")
            }
    )
    public User addAdmin(@RequestBody UserDto dto) {
        return service.addAdmin(dto);
    }


    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "This endpoint handles user login and returns a JWT token upon successful authentication.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
            }
    )
    public String login(@RequestBody LoginDto loginRequest) {
        return service.login(loginRequest);
    }

    @PutMapping("/revokeToken/{userId}/{token}")
    @Operation(
            summary = "Revoke user token",
            description = "This endpoint allows revoking a user's token, rendering it invalid for future use.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token successfully revoked")
            }
    )
    public UpdateResult revokeToken(@PathVariable String userId, @PathVariable String token) {
        return service.revokeToken(userId, token);
    }

}
