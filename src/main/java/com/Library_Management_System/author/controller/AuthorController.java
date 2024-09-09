package com.Library_Management_System.author.controller;

import com.Library_Management_System.author.dto.AuthorDto;
import com.Library_Management_System.author.AuthorService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/author", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("@methodAuthorization.hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Add a new author",
            description = "This endpoint adds a new author to the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Author successfully added"),
                    @ApiResponse(responseCode = "403", description = "You are not authorized to perform this action"),
                    @ApiResponse(responseCode = "404", description = "Bad request")
            }
    )
    public ResponseEntity<AuthorDto> addAuthor(@RequestBody AuthorDto dto) {
        return ResponseEntity.ok().body(service.addAuthor(dto));
    }

    @PutMapping("/{authorId}")
    @PreAuthorize("@methodAuthorization.hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Update an existing author",
            description = "This endpoint updates an author's details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Author successfully updated"),
                    @ApiResponse(responseCode = "404", description = "Author not found"),
                    @ApiResponse(responseCode = "403", description = "You are not authorized to perform this action")
            }
    )
    public ResponseEntity<UpdateResult> updateAuthor(@RequestBody AuthorDto dto, @PathVariable String authorId) {
        return ResponseEntity.ok().body(service.updateAuthor(dto, authorId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@methodAuthorization.hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Delete an existing author",
            description = "This endpoint deletes an author and all its related books",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Author successfully deleted"),
                    @ApiResponse(responseCode = "403", description = "You are not authorized to perform this action")
            }
    )
    public ResponseEntity<DeleteResult> deleteAuthor(@PathVariable String id) {
        return ResponseEntity.ok().body(service.delete(id));
    }

    @GetMapping
    @PreAuthorize("@methodAuthorization.hasAuthority('read')")
    @Operation(
            summary = "Retrieve all authors",
            description = "This endpoint returns a list of all authors in the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authors retrieved successfully"),
                    @ApiResponse(responseCode = "403", description = "You are not authorized to perform this action")
            }
    )
    public ResponseEntity<List<AuthorDto>> retrieveAllAuthors() {
        return ResponseEntity.ok(service.retrieveAllAuthors());
    }

    @GetMapping("/getDetails/{id}")
    @PreAuthorize("@methodAuthorization.hasAuthority('read')")
    @Operation(
            summary = "Get author details",
            description = "This endpoint retrieves the details of a specific author by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Author details retrieved successfully"),
                    @ApiResponse(responseCode = "403", description = "You are not authorized to perform this action")
            }
    )
    public ResponseEntity<AuthorDto> getAuthorDetails(@PathVariable String id) {
        return ResponseEntity.ok().body(service.getAuthorDetails(id));
    }
}
