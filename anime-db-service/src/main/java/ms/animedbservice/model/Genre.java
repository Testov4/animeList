package ms.animedbservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;
import java.util.List;

@Data
@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @Column(name = "mal_id")
    Integer malId;

    String type;

    String name;

    String url;

    @ManyToMany(mappedBy = "genres")
    @ToString.Exclude
    List<Anime> animes;

}
