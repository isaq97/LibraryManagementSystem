package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Magazine  extends LibraryItem{
    private int issueNumber;
    final static int loanDays=7;


    public Magazine(int id, String title, int year, Genre genre, int issueNumber) {
        super(id, title, year, genre);
        this.issueNumber = issueNumber;
    }

    @Override
    public int getLoanDays() {
        return loanDays;
    }

    @Override
    public boolean matches(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return false;
        }
        return super.matches(keyword);
    }
}
