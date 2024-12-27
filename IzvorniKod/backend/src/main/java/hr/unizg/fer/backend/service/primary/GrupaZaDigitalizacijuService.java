package hr.unizg.fer.backend.service.primary;

import hr.unizg.fer.backend.model.primary.GrupaZaDigitalizaciju;
import hr.unizg.fer.backend.model.primary.StatusDigitalizacije;

import java.util.List;
import java.util.Map;

public interface GrupaZaDigitalizacijuService {
     Map<StatusDigitalizacije, Integer> getFilmCountByStatus();
     List<Object[]> countGroupsTakenOutByUser();
     List<Object[]> countGroupsReturnedByUser();
     GrupaZaDigitalizaciju addFilms(List<String> nasloviFilmova, GrupaZaDigitalizaciju grupaZaDigitalizaciju);
     List<GrupaZaDigitalizaciju> getAll();
}
