package joljak.homecoming.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PetQrInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String furColor;
    private String likes;
    private String dislikes;
    private String region;
    private String phoneNumber;
    private String manual;

}
