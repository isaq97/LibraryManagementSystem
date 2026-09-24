package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public abstract class LibraryItem implements Searchable {
    private int id;
    private String title;
    private int year;
    private Genre genre;

    public LibraryItem(int id, String title, int year, Genre genre) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
    }

    public abstract int getLoanDays();
    @Override
    public boolean matches(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return false;
        }
        return title != null && title.toLowerCase().contains(keyword.toLowerCase());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryItem that = (LibraryItem) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}