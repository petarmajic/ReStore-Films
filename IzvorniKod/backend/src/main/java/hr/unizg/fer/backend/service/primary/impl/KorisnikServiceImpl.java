package hr.unizg.fer.backend.service.primary.impl;

import hr.unizg.fer.backend.model.primary.*;
import hr.unizg.fer.backend.repository.primary.GrupaZaDigitalizacijuRepository;
import hr.unizg.fer.backend.repository.primary.KorisnikRepository;
import hr.unizg.fer.backend.service.primary.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class KorisnikServiceImpl implements KorisnikService {
    private final KorisnikRepository korisnikRepository;
    private final GrupaZaDigitalizacijuRepository grupaZaDigitalizacijuRepository;

    @Autowired
    public KorisnikServiceImpl(KorisnikRepository korisnikRepository, GrupaZaDigitalizacijuRepository grupaZaDigitalizacijuRepository) {
        this.korisnikRepository = korisnikRepository;
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

        noviKorisnik.setIme(korisnik.getIme());
        noviKorisnik.setPrezime(korisnik.getPrezime());
        noviKorisnik.setEmail(korisnik.getEmail());
        noviKorisnik.setUloga(UlogaKorisnika.DJELATNIK);
        noviKorisnik.setIznioIzSkladistaGrupeZaDigitalizaciju(new ArrayList<>());
        noviKorisnik.setVratioUSkladisteGrupeZaDigitalizaciju(new ArrayList<>());

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

        // potencijalno treba dodati handlanje ovoga -> ako želimo to ovdje moći
        if (korisnik.getIznioIzSkladistaGrupeZaDigitalizaciju() != null) {
            // Dohvati postojeću listu ili kreiraj novu ako je null
            List<Long> grupeZaDigIds = postojeciKorisnik.getIznioIzSkladistaGrupeZaDigitalizaciju() != null
                    ? new ArrayList<>(postojeciKorisnik.getIznioIzSkladistaGrupeZaDigitalizaciju())
                    : new ArrayList<>();

            for (Long id : korisnik.getIznioIzSkladistaGrupeZaDigitalizaciju()) {
                if (grupaZaDigitalizacijuRepository.findById(id).isPresent()) {
                    // Provjeri da li ID već postoji u listi
                    if (!grupeZaDigIds.contains(id)) {
                        grupeZaDigIds.add(id);
                        // Ažuriraj grupu za digitalizaciju
                        GrupaZaDigitalizaciju grupaZaDigitalizaciju = grupaZaDigitalizacijuRepository.findById(id).get();
                        grupaZaDigitalizaciju.setIznioIzSkladistaKorisnikId(postojeciKorisnik.getIdKorisnika());
                        grupaZaDigitalizaciju.setStatusDigitalizacije(StatusDigitalizacije.NA_DIGITALIZACIJI);
                        grupaZaDigitalizacijuRepository.save(grupaZaDigitalizaciju);
                    }
                } else {
                    throw new IllegalArgumentException("Nepostojeca grupa za digitalizaciju!");
                }
            }
            postojeciKorisnik.setIznioIzSkladistaGrupeZaDigitalizaciju(grupeZaDigIds);
        }
<<<<<<< HEAD

        if (korisnik.getVratioUSkladisteGrupeZaDigitalizaciju() != null) {
            // Dohvati postojeću listu ili kreiraj novu ako je null
            List<Long> grupeZaDigIds = postojeciKorisnik.getVratioUSkladisteGrupeZaDigitalizaciju() != null
                    ? new ArrayList<>(postojeciKorisnik.getVratioUSkladisteGrupeZaDigitalizaciju())
                    : new ArrayList<>();

            for (Long id : korisnik.getVratioUSkladisteGrupeZaDigitalizaciju()) {
                if (grupaZaDigitalizacijuRepository.findById(id).isPresent()) {
                    // Provjeri da li ID već postoji u listi
                    if (!grupeZaDigIds.contains(id)) {
                        grupeZaDigIds.add(id);
                        // Ažuriraj grupu za digitalizaciju
                        GrupaZaDigitalizaciju grupaZaDigitalizaciju = grupaZaDigitalizacijuRepository.findById(id).get();
                        grupaZaDigitalizaciju.setVratioUSkladisteKorisnikId(postojeciKorisnik.getIdKorisnika());
                        grupaZaDigitalizaciju.setStatusDigitalizacije(StatusDigitalizacije.ZAVRSENO);
                        grupaZaDigitalizacijuRepository.save(grupaZaDigitalizaciju);
                    }
                } else {
=======
        if(korisnik.getVratioUSkladisteGrupeZaDigitalizaciju() != null){
            List<Long> grupeZaDigIds = new ArrayList<>();
            for(Long id: korisnik.getVratioUSkladisteGrupeZaDigitalizaciju()){
                if(grupaZaDigitalizacijuRepository.findById(id).isPresent()){
                    grupeZaDigIds.add(id);
                    // dodavanje vratioUSkladisteKorisnikId u grupi za digitalizaciju
                    GrupaZaDigitalizaciju grupaZaDigitalizaciju = grupaZaDigitalizacijuRepository.findById(id).get();
                    grupaZaDigitalizaciju.setVratioUSkladisteKorisnikId(postojeciKorisnik.getIdKorisnika());
                    grupaZaDigitalizaciju.setStatusDigitalizacije(StatusDigitalizacije.ZAVRSENO);
                    grupaZaDigitalizaciju.setVrijemeZavrsetka(LocalDateTime.now());
                    grupaZaDigitalizacijuRepository.save(grupaZaDigitalizaciju);
                } else{
>>>>>>> b015fa0 (backend/ popravljeno vrijeme zavrsetka digitalizacije)
                    throw new IllegalArgumentException("Nepostojeca grupa za digitalizaciju!");
                }
            }
            postojeciKorisnik.setVratioUSkladisteGrupeZaDigitalizaciju(grupeZaDigIds);
        }

        return korisnikRepository.save(postojeciKorisnik);
    }
}
