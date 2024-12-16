package hr.unizg.fer.backend.service.primary;

import hr.unizg.fer.backend.model.primary.Korisnik;

public interface KorisnikService {
    Korisnik getKorisnikByEmail(String email);

    Korisnik addKorisnik(Korisnik korisnik);
}
