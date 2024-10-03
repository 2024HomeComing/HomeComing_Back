package joljak.homecoming.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AllboardDTO {
    private long id;
    private String title;
    private String breed;
    private String name;
    private String size;
    private String age;
    private String color;
    private String characteristics;
    private String lastSeenLocation;
    private String lastSeenTime;
    private String contact;
    private String additionalInfo;
    private LocalDateTime createdAt;
    private String imageUrl;
    private String kind;

    public AllboardDTO(long id, String title, String breed, String name, String size, String age, String color, String characteristics, String lastSeenLocation, String lastSeenTime, String contact, String additionalInfo, LocalDateTime createdAt, String imageUrl, String kind) {
        this.id = id;
        this.title = title;
        this.breed = breed;
        this.name = name;
        this.size = size;
        this.age = age;
        this.color = color;
        this.characteristics = characteristics;
        this.lastSeenLocation = lastSeenLocation;
        this.lastSeenTime = lastSeenTime;
        this.contact = contact;
        this.additionalInfo = additionalInfo;
        this.createdAt = createdAt;
        this.imageUrl = imageUrl;
        this.kind = kind;
    }
}
