package hr.unizg.fer.backend.service.primary;

import hr.unizg.fer.backend.model.primary.Korisnik;

import java.nio.file.AccessDeniedException;

public interface KorisnikService {
    Korisnik getKorisnikByEmail(String email);

    Korisnik addKorisnik(Korisnik korisnik);

    Void deleteKorisnik(String ulogaKorisnika, String email) throws AccessDeniedException;
}
