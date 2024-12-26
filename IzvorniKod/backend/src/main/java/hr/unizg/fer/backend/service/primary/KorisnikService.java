package hr.unizg.fer.backend.service.primary;

import hr.unizg.fer.backend.model.primary.Korisnik;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface KorisnikService {
    Korisnik getKorisnikByEmail(String email); // Dohvaćanje korisnika po emailu

    Korisnik addKorisnik(Korisnik korisnik); // Dodavanje korisnika

    void deleteKorisnik(String ulogaKorisnika, String email) throws AccessDeniedException; // Brisanje korisnika

    List<Korisnik> getAllKorisnici(); // Dohvaćanje svih korisnika

    Korisnik updateKorisnik(String ulogaKorisnika, String emailZaUpdate, Korisnik korisnik) throws AccessDeniedException;
}
