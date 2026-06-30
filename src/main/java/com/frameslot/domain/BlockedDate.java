package com.frameslot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class BlockedDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String reason;

    protected BlockedDate() {
    }

    public BlockedDate(Studio studio, LocalDate date, String reason) {
        this.studio = studio;
        this.date = date;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public Studio getStudio() {
        return studio;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getReason() {
        return reason;
    }
}
