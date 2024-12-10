package hr.unizg.fer.backend.service;

import hr.unizg.fer.backend.model.primary.StatusDigitalizacije;

import java.util.Map;

public interface GrupaZaDigitalizacijuService {
     Map<StatusDigitalizacije, Long> getFilmCountByStatus();
}
