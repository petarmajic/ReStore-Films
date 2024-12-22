package hr.unizg.fer.backend.service.primary;

import hr.unizg.fer.backend.model.primary.FilmskaTraka;
import hr.unizg.fer.backend.model.secondary.FilmskaTrakaArhiva;

import java.util.List;

public interface FilmskaTrakaService {
    List<FilmskaTraka> getAllFilmskeTrake();
    FilmskaTraka getFilmskaTrakaById(Long id);
    FilmskaTraka getFilmskaTrakaByNaslov(String naslov);
    FilmskaTraka updateFilmskaTraka(Long id, FilmskaTraka updatedTraka);
    FilmskaTraka addFilmskaTraka(FilmskaTrakaArhiva newTraka);
}

