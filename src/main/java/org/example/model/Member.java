package org.example.model;

public record Member(int id, String name) {
    public Member {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Ad boş ola bilməz!");
        }
    }
}