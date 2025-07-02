package com.libraryManagement.controller;

import com.libraryManagement.dto.requestDto.BookRequestDto;
import com.libraryManagement.dto.responseDto.BookResponseDto;
import com.libraryManagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private final BookService bookService;

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    @PostMapping("/add")
    public ResponseEntity<BookResponseDto> addBook(@RequestBody BookRequestDto book) {
        BookResponseDto created = bookService.addBook(book);
        logger.info("Book Added Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/getbooks")
    public ResponseEntity<List<BookResponseDto>> getBookList() {
        List<BookResponseDto> books = bookService.getAllBooks();
        logger.info("Book Fetched Successfully");
        return ResponseEntity.ok(books);
    }

    @PostMapping("/upload-image")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            String uploadDir = "uploads/";
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            String imageUrl = "/uploads/" + fileName; // accessible from frontend
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload");
        }
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('MEMBER')")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable int id) {
        Optional<BookResponseDto> bookOpt = bookService.getBookById(id);
        return bookOpt.map(response -> ResponseEntity.status(HttpStatus.OK).body(response)).
                orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable int id, @RequestParam int availableCopies) {
        BookResponseDto updated = bookService.updateBook(id, availableCopies);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<BookResponseDto>> searchByTitle(@RequestParam String title) {
        List<BookResponseDto> results = bookService.searchByTitle(title);
        logger.info("Book with a specific title Fetched Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @GetMapping("/search/author")
    public ResponseEntity<List<BookResponseDto>> searchByAuthor(@RequestParam String author) {
        List<BookResponseDto> results = bookService.searchByAuthor(author);
        logger.info("Book with a specific Author Fetched Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @GetMapping("/search/genre")
    public ResponseEntity<List<BookResponseDto>> searchByGenre(@RequestParam String genre) {
        List<BookResponseDto> results = bookService.searchByGenre(genre);
        logger.info("Book with a specific Genre Fetched Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }
}