package ms.animeservice.service.implementation;

import ms.animeservice.exception.AnimeNotFound;
import ms.animeservice.model.Anime;
import ms.animeservice.util.AnimeSearchRequest;
import ms.animeservice.model.Genre;
import ms.animeservice.repository.AnimeRepository;
import ms.animeservice.repository.GenreRepository;
import ms.animeservice.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnimeServiceImpl implements AnimeService {

    private final AnimeRepository animeRepository;

    private final GenreRepository genreRepository;

    @Override
    public List<Anime> findAnimeListByIds(List<Integer> ids) {
        return animeRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public List<Anime> findAnimeByTitleAndTypeAndGenres(AnimeSearchRequest request) {
        List<Genre> genres = genreRepository.findAllById(request.getGenres());

        List<Anime> animeList = animeRepository.findByTitleContainingIgnoreCase(request.getTitle())
            .stream()
            .filter(anime -> anime.getType().contains(request.getType()))
            .filter(anime -> genres.isEmpty() || anime.getGenres()
                .stream()
                .anyMatch(genres::contains))
            .collect(Collectors.toList());

        initializeImagesCollection(animeList);
        return animeList;
    }

    @Override
    @Transactional
    public void saveNotPresentAnimeList(List<Anime> animeList) {
        List<Anime> foundAnimeList = animeRepository.findAllById(makeSetOfIdFromAnimeList(animeList));

        Set<Integer> foundAnimeIdSet = makeSetOfIdFromAnimeList(foundAnimeList);

        List<Anime> notFoundAnime = animeList
            .stream()
            .filter(anime -> !foundAnimeIdSet.contains(anime.getMalId()))
            .collect(Collectors.toList());

        animeRepository.saveAll(notFoundAnime);
    }

    @Override
    public Anime findAnimeByIdAndFetchAll(Integer id) {
            Anime anime = animeRepository.findById(id)
                .orElseThrow(() -> new AnimeNotFound("Anime was not found in the database, anime id: " + id));
            initializeAnime(anime);
            return anime;
    }

    private void initializeImagesCollection(List<Anime> animeList) {
        animeList.forEach(anime -> Hibernate.initialize(anime.getImages()));
    }

    private void initializeAnime(Anime anime) {
        Hibernate.initialize(anime.getImages());
        Hibernate.initialize(anime.getGenres());
        Hibernate.initialize(anime.getTitleSynonyms());
        Hibernate.initialize(anime.getStudios());
    }

    private Set<Integer> makeSetOfIdFromAnimeList(List<Anime> animeList) {
        return animeList.stream()
            .map(Anime::getMalId)
            .collect(Collectors.toSet());
    }
}
