package com.Library_Management_System.book.controller;

import com.Library_Management_System.book.dto.BookDto;
import com.Library_Management_System.book.service.BookService;
import com.mongodb.client.result.DeleteResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("@methodAuthorization.hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Add a new book",
            description = "This endpoint allows administrators to add a new book to the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book successfully added"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - Only admin users can access this endpoint"),
                    @ApiResponse(responseCode = "404", description = "Bad Request - Invalid input data")
            }
    )
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto book) {
        return ResponseEntity.ok(service.addBook(book));
    }

    @PutMapping
    @PreAuthorize("@methodAuthorization.hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Update an existing book",
            description = "This endpoint allows administrators to update book details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book successfully updated"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - Only admin users can access this endpoint"),
                    @ApiResponse(responseCode = "404", description = "Bad Request - Author does not exists")
            }
    )
    public ResponseEntity<BookDto> update(@RequestBody BookDto book) {
        return ResponseEntity.ok(service.update(book));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@methodAuthorization.hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Delete a book",
            description = "This endpoint allows administrators to delete a book from the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book successfully deleted"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - Only admin users can access this endpoint")
            }
    )
    public ResponseEntity<DeleteResult> delete(@PathVariable String id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping
    @PreAuthorize("@methodAuthorization.hasAuthority('read')")
    @Operation(
            summary = "Retrieve all books",
            description = "This endpoint returns a list of all books in the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Books retrieved successfully"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission to access this resource")
            }
    )
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(service.getAllBooks());
    }

    @GetMapping("/{id}")
    @PreAuthorize("@methodAuthorization.hasAuthority('read')")
    @Operation(
            summary = "Get book details",
            description = "This endpoint retrieves detailed information about a specific book by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book details retrieved successfully"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission to access this resource")
            }
    )
    public ResponseEntity<BookDto> getBookDetails(@PathVariable String id) {
        return ResponseEntity.ok(service.getBookDetails(id));
    }
}
