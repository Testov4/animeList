package ms.animedbservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import java.util.List;

@Data
@Entity
@Table(name = "anime")
public class Anime {

    @Id
    @Column(name = "mal_id")
    private Integer malId;

    private String url;

    @OneToMany(mappedBy = "anime", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @ToString.Exclude
    private List<AnimeImage> images;

    private String title;

    @Column(name = "title_english")
    private String titleEnglish;

    @Column(name = "title_japanese")
    private String titleJapanese;

    @OneToMany(mappedBy = "anime", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private List<TitleSynonym> titleSynonyms;

    private String type;
    private Integer episodes;
    private Boolean airing;
    private String status;
    private String duration;
    private Integer year;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    @ToString.Exclude
    @JoinTable(
        name = "anime_genre",
        joinColumns = @JoinColumn(name = "anime_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    @ToString.Exclude
    @JoinTable(
        name = "anime_studio",
        joinColumns = @JoinColumn(name = "anime_id"),
        inverseJoinColumns = @JoinColumn(name = "studio_id"))
    private List<Studio> studios;
}
