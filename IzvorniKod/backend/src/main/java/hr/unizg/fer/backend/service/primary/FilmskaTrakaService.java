package hr.unizg.fer.backend.service.primary;

import hr.unizg.fer.backend.model.primary.FilmskaTraka;

import java.util.List;

public interface FilmskaTrakaService {
    List<FilmskaTraka> getAllFilmskeTrake();
    FilmskaTraka getFilmskaTrakaById(Long id);
    FilmskaTraka updateFilmskaTraka(Long id, FilmskaTraka updatedTraka);
}

