package hr.unizg.fer.backend.service.secondary;

import hr.unizg.fer.backend.model.secondary.FilmskaTrakaArhiva;


public interface FilmskaTrakaArhivaService {
    FilmskaTrakaArhiva getFilmskaTrakaArhivaByBarKod(String barKod);
}