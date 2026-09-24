package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Book  extends LibraryItem{
    private String author;
    private int pages;
    final static int loanDays=14;

    public Book(int id, String title, int year, Genre genre, String author, int pages) {
        super(id, title, year, genre);
        this.author = author;
        this.pages = pages;
    }

    @Override
    public  int getLoanDays() {
        return loanDays;
    }

    @Override
    public boolean matches(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return false;
        }
        boolean matchesTitle = super.matches(keyword);
        boolean matchesAuthor = author != null && author.toLowerCase().contains(keyword.toLowerCase());
        return matchesTitle || matchesAuthor;
    }
}
