package joljak.homecoming.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reporterName;
    private String phoneNumber;
    private String details;
    private LocalDateTime reportDate;

    @PrePersist
    protected  void onCreate() {
        reportDate = LocalDateTime.now();
    }

    @ManyToOne
    private PetInfo petInfo;

}
