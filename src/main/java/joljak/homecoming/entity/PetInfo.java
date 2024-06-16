package joljak.homecoming.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PetInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String species;
    private String hairColor;
    private String likeDislike;
    private String location;
    private String phoneNumber;
    private String manual;
    private byte[] qrCodeImage;
    private String imageUrl;

    @ManyToOne
    private User user;

}
