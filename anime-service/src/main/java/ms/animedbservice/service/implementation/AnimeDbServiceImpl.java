package ms.animedbservice.service.implementation;

import ms.animedbservice.model.Anime;
import ms.animedbservice.model.AnimeSearchRequest;
import ms.animedbservice.model.Genre;
import ms.animedbservice.repository.AnimeRepository;
import ms.animedbservice.repository.GenreRepository;
import ms.animedbservice.service.AnimeDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnimeDbServiceImpl implements AnimeDbService {

    private final AnimeRepository animeRepository;

    private final GenreRepository genreRepository;

    @Override
    public List<Anime> findAnimeListByIds(List<Integer> ids) {
        return ids.stream()
            .map(id -> animeRepository.findById(id))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    @Override
    public List<Anime> findAnimeByParameters(AnimeSearchRequest request) {
        List<Genre> genres = genresIdListToGenreList(request.getGenres());
        return animeRepository.findByTitleLikeIgnoreCase(request.getQ())
            .stream()
            .filter(anime -> request.getType().map(value -> value.equals(anime.getType())).orElse(true))
            .filter(anime -> {
                if (!genres.isEmpty()) {
                    return anime.getGenres().stream()
                        .anyMatch(genre -> genres.contains(genre));
                } else {
                    return true;
                }
            })
            .collect(Collectors.toList());
    }

    @Override
    public void saveNotPresentAnimeList(List<Anime> animeList) {
        animeList.stream()
            .filter(anime -> findAnimeById(anime.getMalId()).isEmpty())
            .forEach(animeRepository::save);
    }

    @Transactional
    public void saveAnime(Anime anime) {
        animeRepository.save(anime);
    }

    @Transactional
    public void deleteAnime(Anime anime) {
        animeRepository.delete(anime);
    }

    public List<Genre> genresIdListToGenreList(List<Integer> ids) {
        return ids.stream()
            .map(g -> genreRepository.findById(g))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }
    public Optional<Anime> findAnimeById(Integer id) {
        return animeRepository.findById(id);
    }

}
