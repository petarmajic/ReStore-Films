package hr.unizg.fer.backend.service.primary.impl;

import hr.unizg.fer.backend.model.primary.FilmskaTraka;
import hr.unizg.fer.backend.model.primary.GrupaZaDigitalizaciju;
import hr.unizg.fer.backend.model.primary.StatusDigitalizacije;
import hr.unizg.fer.backend.repository.primary.FilmskaTrakaRepository;
import hr.unizg.fer.backend.repository.primary.GrupaZaDigitalizacijuRepository;
import hr.unizg.fer.backend.service.primary.GrupaZaDigitalizacijuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GrupaZaDigitalizacijuServiceImpl implements GrupaZaDigitalizacijuService {

    private final GrupaZaDigitalizacijuRepository grupaZaDigitalizacijuRepository;
    private final FilmskaTrakaRepository filmskaTrakaRepository;

    @Autowired
    public GrupaZaDigitalizacijuServiceImpl(GrupaZaDigitalizacijuRepository grupaZaDigitalizacijuRepository, FilmskaTrakaRepository filmskaTrakaRepository) {
        this.grupaZaDigitalizacijuRepository = grupaZaDigitalizacijuRepository;
        this.filmskaTrakaRepository = filmskaTrakaRepository;
    }

    public Map<StatusDigitalizacije, Integer> getFilmCountByStatus() {
        List<Object[]> results = grupaZaDigitalizacijuRepository.countFilmsByStatus();
        Map<StatusDigitalizacije, Integer> statusCounts = new EnumMap<>(StatusDigitalizacije.class);
        for (Object[] result : results) {
            statusCounts.put((StatusDigitalizacije) result[0], ((Number)result[1]).intValue());
        }
        return statusCounts;
    }
    @Override
    public List<Object[]> countGroupsTakenOutByUser() {
        return grupaZaDigitalizacijuRepository.countGroupsTakenOutByUser();
    }

    @Override
    public List<Object[]> countGroupsReturnedByUser() {
        return grupaZaDigitalizacijuRepository.countGroupsReturnedByUser();
    }

    @Override
    public GrupaZaDigitalizaciju addFilms(List<String> nasloviFilmova, GrupaZaDigitalizaciju grupaZaDigitalizaciju) {
        Set<FilmskaTraka> filmskaTrakaSet = new HashSet<>();
        for(String naslov : nasloviFilmova) {
            if(filmskaTrakaRepository.findFilmskaTrakaByNaslov(naslov).isPresent()){
                filmskaTrakaSet.add(filmskaTrakaRepository.findFilmskaTrakaByNaslov(naslov).get());
            }
            else{
                throw new NoSuchElementException("Ne postojeći film u bazi imena: " + naslov + " !");
            }
        }

        grupaZaDigitalizaciju.setFilmskeTrake(filmskaTrakaSet);
        return grupaZaDigitalizacijuRepository.save(grupaZaDigitalizaciju);
    }

    @Override
    public List<GrupaZaDigitalizaciju> getAll() {
        return grupaZaDigitalizacijuRepository.findAll();
    }


}
