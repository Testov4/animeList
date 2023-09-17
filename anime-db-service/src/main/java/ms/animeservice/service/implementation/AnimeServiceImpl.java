package ms.animeservice.service.implementation;

import ms.animeservice.exception.AnimeNotFound;
import ms.animeservice.model.Anime;
import ms.animeservice.payload.AnimeSearchPayload;
import ms.animeservice.model.Genre;
import ms.animeservice.repository.AnimeRepository;
import ms.animeservice.repository.GenreRepository;
import ms.animeservice.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnimeServiceImpl implements AnimeService {

    private final AnimeRepository animeRepository;

    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Anime> findAnimeListByIds(List<Integer> ids) {
        return animeRepository.findAllByMalId(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Anime> findAnimeByTitleAndTypeAndGenres(AnimeSearchPayload request) {
        List<Genre> genres = genreRepository.findAllById(request.getGenres());

        List<Anime> animeList = animeRepository.findByTitleContains(request.getTitle())
            .stream()
            .filter(anime -> request.getType() == null || anime.getType().contains(request.getType()))
            .filter(anime -> genres.isEmpty() || anime.getGenres()
                .stream()
                .anyMatch(genres::contains))
            .collect(Collectors.toList());

        return animeList;
    }

    @Override
    @Transactional
    public void saveNotPresentAnimeList(List<Anime> animeList) {
        List<Integer> presentAnimeIds = animeRepository.findPresentAnimeList(animeList);

        List<Anime> notFoundAnime = animeList
            .stream()
            .filter(anime -> !presentAnimeIds.contains(anime.getMalId()))
            .collect(Collectors.toList());

        animeRepository.saveAll(notFoundAnime);
    }

    @Override
    @Transactional(readOnly = true)
    public Anime findAnimeByIdAndFetchAll(Integer id) {
        return animeRepository.findById(id)
            .orElseThrow(() -> new AnimeNotFound("Anime was not found in the database, anime id: " + id));
    }

}
