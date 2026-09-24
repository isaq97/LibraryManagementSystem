package org.example.service;

import org.example.exception.*;
import org.example.model.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {
    private final Map<Integer, LibraryItem> items = new HashMap<>();
    private final Map<Integer, BorrowRecord> activeBorrows = new HashMap<>();
    private record BorrowRecord(int memberId, int itemId, LocalDate borrowDate) {}
    private final Map<Integer, Member> members = new HashMap<>();

    public void addItem(LibraryItem item) {
        items.put(item.getId(), item);
    }


    public void addMember(Member member) {
        members.put(member.id(), member);
    }

    public void borrowItem(int memberId, int itemId, LocalDate date)  {
        if (!members.containsKey(memberId)) {
            throw new MemberNotFoundException("Verilən id ilə üzv tapılmadı: " + memberId);
        }
        if (!items.containsKey(itemId)) {
            throw new ItemNotFoundException("Verilən id ilə material tapılmadı: " + itemId);
        }
        if (activeBorrows.containsKey(itemId)) {
            throw new ItemAlreadyBorrowedException("Material artıq başqası tərəfindən götürülüb: " + itemId);
        }

        long currentBorrowedCount = activeBorrows.values().stream()
                .filter(record -> record.memberId() == memberId)
                .count();

        if (currentBorrowedCount >= 3) {
            throw new BorrowLimitExceededException("Üzvün artıq 3 materialı var! ");
        }

        activeBorrows.put(itemId, new BorrowRecord(memberId, itemId, date));
    }
    public double returnItem(int itemId, LocalDate returnDate)  {
        if (!items.containsKey(itemId)) {
            throw new ItemNotFoundException("Verilən id ilə material tapılmadı: " + itemId);
        }
        if (!activeBorrows.containsKey(itemId)) {
            throw new ItemNotBorrowedException("Götürülməmiş materialı qaytarmağa cəhd edilir: " + itemId);
        }

        BorrowRecord record = activeBorrows.remove(itemId);
        LibraryItem item = items.get(itemId);

        LocalDate dueDate = record.borrowDate().plusDays(item.getLoanDays());

        if (returnDate.isAfter(dueDate)) {
            long overdueDays = ChronoUnit.DAYS.between(dueDate, returnDate);
            return overdueDays * 0.50;
        }

        return 0.0;
    }
    public List<Book> getBooksAfter2000SortedByYear() {
        return items.values().stream()
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item)
                .filter(book -> book.getYear() > 2000)
                .sorted(Comparator.comparingInt(Book::getYear))
                .collect(Collectors.toList());
    }

    public Map<Genre, List<LibraryItem>> groupItemsByGenre() {
        return items.values().stream()
                .collect(Collectors.groupingBy(LibraryItem::getGenre));
    }

    public Optional<Book> getBookWithMaxPages() {
        return items.values().stream()
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item)
                .max(Comparator.comparingInt(Book::getPages));
    }


    public Comparator<LibraryItem> getItemYearThenTitleComparator() {
        return Comparator.comparingInt(LibraryItem::getYear)
                .thenComparing(LibraryItem::getTitle, String.CASE_INSENSITIVE_ORDER);
    }


}
