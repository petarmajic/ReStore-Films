package hr.unizg.fer.backend.service.primary;

import hr.unizg.fer.backend.model.primary.GrupaZaDigitalizaciju;
import hr.unizg.fer.backend.model.primary.StatusDigitalizacije;

import java.util.List;
import java.util.Map;

public interface GrupaZaDigitalizacijuService {
     Long getFilmCountByStatus(StatusDigitalizacije statusDigitalizacije);
     Long countGroupsTakenOutByUser(Long idKorisnika);
     Long countGroupsReturnedByUser(Long idKorisnika);
     GrupaZaDigitalizaciju addFilms(List<String> nasloviFilmova, GrupaZaDigitalizaciju grupaZaDigitalizaciju);
     List<GrupaZaDigitalizaciju> getAll();
//     GrupaZaDigitalizaciju updateGroup(Long idGrupe, String emailDjelatnika);
}
