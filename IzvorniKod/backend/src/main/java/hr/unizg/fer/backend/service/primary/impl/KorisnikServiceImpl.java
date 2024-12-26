package hr.unizg.fer.backend.service.primary.impl;

import hr.unizg.fer.backend.model.primary.GrupaZaDigitalizaciju;
import hr.unizg.fer.backend.model.primary.Korisnik;
import hr.unizg.fer.backend.model.primary.StatistikaDigitalizacije;
import hr.unizg.fer.backend.model.primary.UlogaKorisnika;
import hr.unizg.fer.backend.repository.primary.GrupaZaDigitalizacijuRepository;
import hr.unizg.fer.backend.repository.primary.KorisnikRepository;
import hr.unizg.fer.backend.repository.primary.StatistikaDigitalizacijeRepository;
import hr.unizg.fer.backend.service.primary.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.*;

@Service
public class KorisnikServiceImpl implements KorisnikService {
    private final KorisnikRepository korisnikRepository;
    private final StatistikaDigitalizacijeRepository statistikaDigitalizacijeRepository;
    private final GrupaZaDigitalizacijuRepository grupaZaDigitalizacijuRepository;

    @Autowired
    public KorisnikServiceImpl(KorisnikRepository korisnikRepository, StatistikaDigitalizacijeRepository statistikaDigitalizacijeRepository, GrupaZaDigitalizacijuRepository grupaZaDigitalizacijuRepository) {
        this.korisnikRepository = korisnikRepository;
        this.statistikaDigitalizacijeRepository = statistikaDigitalizacijeRepository;
        this.grupaZaDigitalizacijuRepository = grupaZaDigitalizacijuRepository;
    }

    @Override
    public Korisnik getKorisnikByEmail(String email) {
        Optional<Korisnik> korisnik = korisnikRepository.findKorisnikByEmail(email);
        return korisnik.orElse(null);
    }

    // by default je djelatnik
    @Override
    public Korisnik addKorisnik(Korisnik korisnik) {
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
    public void deleteKorisnik(String ulogaKorisnika, String email) throws AccessDeniedException {
        if(!ulogaKorisnika.equals("ADMINISTRATOR")){
            throw new AccessDeniedException("AKCIJA ODBIJENA -> Brisanje provodi samo ADMINISTRATOR !!!");
        }
        if(!korisnikRepository.findKorisnikByEmail(email).isPresent()){
            throw new NoSuchElementException("Ne postoji korisnik s tim emailom!!!");
        }

        korisnikRepository.delete(korisnikRepository.findKorisnikByEmail(email).get());

    }
    @Override
    public List<Korisnik> getAllKorisnici() {
        return korisnikRepository.findAll();
    }

    @Override
    public Korisnik updateKorisnik(String ulogaKorisnika, String emailZaUpdate, Korisnik korisnik)
            throws AccessDeniedException {
        if(!ulogaKorisnika.equals("ADMINISTRATOR")){
            throw new AccessDeniedException("AKCIJA ODBIJENA -> Update provodi samo ADMINISTRATOR !!!");
        }
        if(!korisnikRepository.findKorisnikByEmail(emailZaUpdate).isPresent()){
            throw new NoSuchElementException("Ne postoji korisnik s tim emailom!!!");
        }

        //akcija je odobrena
        Korisnik postojeciKorisnik = korisnikRepository.findKorisnikByEmail(emailZaUpdate).get();
        if(korisnik.getIme() != null){
            postojeciKorisnik.setIme(korisnik.getIme());
        }
        if(korisnik.getPrezime() != null){
            postojeciKorisnik.setPrezime(korisnik.getPrezime());
        }
        if(korisnik.getEmail() != null){
            postojeciKorisnik.setEmail(korisnik.getEmail());
        }
        if(korisnik.getUloga() != null){
            postojeciKorisnik.setUloga(korisnik.getUloga());
        }
        // provjerit ovo -> dali trebam moci kreirati novu statistiku digitalizacije ako vec ne postoji itd...
        // kod nas svaki korinsik by defalt ima statistiku digitalizacije
        if(korisnik.getStatistikaDigitalizacije() != null){
            StatistikaDigitalizacije postojecaStatistikaDigitalizacije =
                    statistikaDigitalizacijeRepository.findById(postojeciKorisnik
                            .getStatistikaDigitalizacije()
                            .getIdStatistike())
                            .orElse(new StatistikaDigitalizacije()); //provjerit ovo orElse

            if(korisnik.getStatistikaDigitalizacije().getBrojDigitaliziranih() != null){
                postojecaStatistikaDigitalizacije.setBrojDigitaliziranih(korisnik.getStatistikaDigitalizacije().getBrojDigitaliziranih());
            }
            if(korisnik.getStatistikaDigitalizacije().getBrojNaDigitalizaciji() != null){
                postojecaStatistikaDigitalizacije.setBrojNaDigitalizaciji(korisnik.getStatistikaDigitalizacije().getBrojNaDigitalizaciji());
            }

            postojeciKorisnik.setStatistikaDigitalizacije(postojecaStatistikaDigitalizacije);
        }
        if(korisnik.getIznioIzSkladistaGrupeZaDigitalizaciju() != null){
            postojeciKorisnik.setIznioIzSkladistaGrupeZaDigitalizaciju(korisnik.getIznioIzSkladistaGrupeZaDigitalizaciju());
        }
        if(korisnik.getVratioUSkladisteGrupeZaDigitalizaciju() != null){
            postojeciKorisnik.setVratioUSkladisteGrupeZaDigitalizaciju(korisnik.getVratioUSkladisteGrupeZaDigitalizaciju());
        }

        return korisnikRepository.save(postojeciKorisnik);
    }
}
