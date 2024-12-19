package hr.unizg.fer.backend.service.primary.impl;

import hr.unizg.fer.backend.model.primary.Korisnik;
import hr.unizg.fer.backend.model.primary.StatistikaDigitalizacije;
import hr.unizg.fer.backend.model.primary.UlogaKorisnika;
import hr.unizg.fer.backend.repository.primary.KorisnikRepository;
import hr.unizg.fer.backend.service.primary.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class KorisnikServiceImpl implements KorisnikService {
    private final KorisnikRepository korisnikRepository;

    @Autowired
    public KorisnikServiceImpl(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    @Override
    public Korisnik getKorisnikByEmail(String email) {
        Optional<Korisnik> korisnik = korisnikRepository.findKorisnikByEmail(email);
        return korisnik.orElse(null);
    }

    // by default je djelatnik
    @Override
    public Korisnik addKorisnik(Korisnik korisnik) {
//        System.out.println(korisnik);
        if(korisnik.getEmail() == null){
            throw new IllegalArgumentException("Email ne smije biti null!!!");
        }
        else if(korisnikRepository.findKorisnikByEmail(korisnik.getEmail()).isPresent()){
            throw new IllegalArgumentException("Vec postoji korisnik s tim emailom!!!");
        }

        Korisnik noviKorisnik = new Korisnik();
        StatistikaDigitalizacije statistikaDigitalizacije = new StatistikaDigitalizacije();
        statistikaDigitalizacije.setBrojDigitaliziranih(0);
        statistikaDigitalizacije.setBrojNaDigitalizaciji(0);

        noviKorisnik.setIme(korisnik.getIme());
        noviKorisnik.setPrezime(korisnik.getPrezime());
        noviKorisnik.setEmail(korisnik.getEmail());
        noviKorisnik.setUloga(UlogaKorisnika.DJELATNIK);
        noviKorisnik.setStatistikaDigitalizacije(statistikaDigitalizacije);
        noviKorisnik.setIznioIzSkladistaGrupeZaDigitalizaciju(new HashSet<>());
        noviKorisnik.setVratioUSkladisteGrupeZaDigitalizaciju(new HashSet<>());

        return korisnikRepository.save(noviKorisnik);
    }

    @Override
    public Void deleteKorisnik(String ulogaKorisnika, String email) throws AccessDeniedException {
        if(!ulogaKorisnika.equals("ADMINISTRATOR")){
            throw new AccessDeniedException("AKCIJA ODBIJENA -> Brisanje provodi samo ADMINISTRATOR !!!");
        }
        if(!korisnikRepository.findKorisnikByEmail(email).isPresent()){
            throw new NoSuchElementException("Ne postoji korisnik s tim emailom!!!");
        }

        korisnikRepository.delete(korisnikRepository.findKorisnikByEmail(email).get());

        return null;
    }
}
