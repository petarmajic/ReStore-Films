package hr.unizg.fer.backend.service.primary;

import hr.unizg.fer.backend.model.primary.GrupaZaDigitalizaciju;
import hr.unizg.fer.backend.model.primary.StatusDigitalizacije;

import java.util.List;
import java.util.Map;

public interface GrupaZaDigitalizacijuService {
     Long getFilmCountByStatus(StatusDigitalizacije statusDigitalizacije);
     Long countGroupsTakenOutByUser(Long idKorisnika);
     Long countGroupsReturnedByUser(Long idKorisnika);
     GrupaZaDigitalizaciju createGroup(GrupaZaDigitalizaciju grupaZaDigitalizaciju);
     List<GrupaZaDigitalizaciju> getAll();
     List<String> getFilmsInGroup(Long idGrupe);
}
