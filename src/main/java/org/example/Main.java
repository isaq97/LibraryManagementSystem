package org.example;

import org.example.exception.BorrowLimitExceededException;
import org.example.exception.ItemAlreadyBorrowedException;
import org.example.exception.ItemNotFoundException;
import org.example.exception.MemberNotFoundException;
import org.example.model.*;
import org.example.service.LibraryService;

import java.time.LocalDate;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ArrayList<LibraryItem> library = new ArrayList<>();


        // 5 Kitab
        Book book1 = new Book(1, "Clean Code", 2008, Genre.TECHNOLOGY,"Joe",70);
        Book book2 = new Book(2, "The Hobbit", 1937, Genre.FICTION,"Joe",86);
        Book book3 = new Book(3, "Sapiens", 2011, Genre.HISTORY,"Joe",97);
        Book book4 = new Book(4, "Astrophysics for People in a Hurry", 2017, Genre.SCIENCE,"Joe",117);
        Book book5 = new Book(5, "Java: The Complete Reference", 2021, Genre.TECHNOLOGY,"Joe",107);
        Magazine mag1 = new Magazine(6, "National Geographic", 2023, Genre.SCIENCE, 142);
        Magazine mag2 = new Magazine(7, "Wired", 2022, Genre.TECHNOLOGY, 88);
        System.out.println(mag2.getLoanDays());
        System.out.println(book1.getLoanDays());

        Member member=new Member(1,"g");
        Member member2=new Member(2,"f");


        try {
            Member member3 = new Member(3, ""); // Bu hissə exception atacaq
        } catch (IllegalArgumentException e) {
            System.out.println( e.getMessage());
        }




       // System.out.println(member.id());

        LibraryService libraryService= new LibraryService();
        //add member
        libraryService.addMember(member2);

        try {
            libraryService.borrowItem(9,book1.getId(),LocalDate.now());
        } catch (MemberNotFoundException e) {
            System.out.println( e.getMessage());
        }
        try {
            libraryService.borrowItem(2,13,LocalDate.now());
        } catch (ItemNotFoundException e) {
            System.out.println( e.getMessage());
        }


//add elements to book
        libraryService.addItem(book1);
        libraryService.addItem(book2);
        libraryService.addItem(book3);
        libraryService.addItem(mag2);

       // libraryService.borrowItem(4,book1.getId(), LocalDate.now());
        libraryService.borrowItem(member2.id(),book1.getId(),LocalDate.now());
        try {
            libraryService.borrowItem(member2.id(),book1.getId(),LocalDate.now());
        } catch (ItemAlreadyBorrowedException e) {
            System.out.println( e.getMessage());
        }
        libraryService.borrowItem(member2.id(),book2.getId(),LocalDate.now());
        libraryService.borrowItem(member2.id(),book3.getId(),LocalDate.now());
        try {
            libraryService.borrowItem(member2.id(),mag2.getId(),LocalDate.now());

        }
        catch (BorrowLimitExceededException e){
            System.out.println(e.getMessage());
        }

        System.out.println(libraryService.getBookWithMaxPages().map(Book::getPages)
                .orElse(0));
        System.out.println(libraryService.returnItem(2,LocalDate.now().plusDays(16)));





    }
}