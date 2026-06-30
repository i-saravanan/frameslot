package com.frameslot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class SessionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionName name;

    @Column(nullable = false)
    private Integer durationHours;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer maxAdvanceDays;

    @Column(nullable = false)
    private boolean active = true;

    protected SessionType() {
    }

    public SessionType(Studio studio, SessionName name, Integer durationHours, BigDecimal price, Integer maxAdvanceDays) {
        this.studio = studio;
        this.name = name;
        this.durationHours = durationHours;
        this.price = price;
        this.maxAdvanceDays = maxAdvanceDays;
    }

    public Long getId() {
        return id;
    }

    public Studio getStudio() {
        return studio;
    }

    public SessionName getName() {
        return name;
    }

    public Integer getDurationHours() {
        return durationHours;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getMaxAdvanceDays() {
        return maxAdvanceDays;
    }

    public boolean isActive() {
        return active;
    }
}
