package com.libraryManagement.dto.requestDto;

public class BookRequestDto {
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private int yearPublished;
    private int availableCopies;
    private String imageUrl;

    public BookRequestDto() {
    }

    public BookRequestDto(String title, String author, String genre, String isbn, int yearPublished, int availableCopies, String imageUrl) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.yearPublished = yearPublished;
        this.availableCopies = availableCopies;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
}