package ms.animedbservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "anime_image")
@Data
public class AnimeImage {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "anime_id")
    @ToString.Exclude
    private Anime anime;

    @Column(name = "image_type")
    private String imageType;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_small_url")
    private String imageSmallUrl;

    @Column(name = "image_large_url")
    private String imageLargeUrl;

}
