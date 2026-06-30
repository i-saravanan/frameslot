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
import java.time.Instant;

@Entity
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(length = 2000)
    private String bio;

    private String instagramLink;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudioStatus status = StudioStatus.PENDING;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected Studio() {
    }

    public Studio(User owner, String name, String location, String bio, String instagramLink) {
        this.owner = owner;
        this.name = name;
        this.location = location;
        this.bio = bio;
        this.instagramLink = instagramLink;
    }

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getBio() {
        return bio;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public StudioStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void updateProfile(String name, String location, String bio, String instagramLink) {
        this.name = name;
        this.location = location;
        this.bio = bio;
        this.instagramLink = instagramLink;
    }

    public void setStatus(StudioStatus status) {
        this.status = status;
    }
}
