package hr.unizg.fer.backend.service.primary.impl;

import hr.unizg.fer.backend.model.primary.FilmskaTraka;
import hr.unizg.fer.backend.model.primary.GrupaZaDigitalizaciju;
import hr.unizg.fer.backend.model.primary.Korisnik;
import hr.unizg.fer.backend.model.primary.StatusDigitalizacije;
import hr.unizg.fer.backend.repository.primary.FilmskaTrakaRepository;
import hr.unizg.fer.backend.repository.primary.GrupaZaDigitalizacijuRepository;
import hr.unizg.fer.backend.repository.primary.KorisnikRepository;
import hr.unizg.fer.backend.service.primary.GrupaZaDigitalizacijuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GrupaZaDigitalizacijuServiceImpl implements GrupaZaDigitalizacijuService {

    private final GrupaZaDigitalizacijuRepository grupaZaDigitalizacijuRepository;
    private final FilmskaTrakaRepository filmskaTrakaRepository;
    private final KorisnikRepository korisnikRepository;

    @Autowired
    public GrupaZaDigitalizacijuServiceImpl(GrupaZaDigitalizacijuRepository grupaZaDigitalizacijuRepository, FilmskaTrakaRepository filmskaTrakaRepository, KorisnikRepository korisnikRepository) {
        this.grupaZaDigitalizacijuRepository = grupaZaDigitalizacijuRepository;
        this.filmskaTrakaRepository = filmskaTrakaRepository;
        this.korisnikRepository = korisnikRepository;
    }

    public Long getFilmCountByStatus(StatusDigitalizacije statusDigitalizacije) {
         return grupaZaDigitalizacijuRepository.countFilmsByStatus(statusDigitalizacije);
    }
    @Override
    public Long countGroupsTakenOutByUser(Long idKorisnika) {
        return grupaZaDigitalizacijuRepository.countGroupsTakenOutByUser(idKorisnika);
    }

    @Override
    public Long countGroupsReturnedByUser(Long idKorisnika) {
        return grupaZaDigitalizacijuRepository.countGroupsReturnedByUser(idKorisnika);
    }

    @Override
    public GrupaZaDigitalizaciju createGroup(GrupaZaDigitalizaciju grupaZaDigitalizaciju) {
        List<String> filmskeTrakeNaslovi = new ArrayList<>();
        for(String naslov : grupaZaDigitalizaciju.getFilmskeTrake()) {
            if(filmskaTrakaRepository.findFilmskaTrakaByNaslov(naslov).isPresent()){
                filmskeTrakeNaslovi.add(naslov);
            }
            else{
                throw new NoSuchElementException("Ne postojeći film u bazi imena: " + naslov + " !");
            }
        }
        GrupaZaDigitalizaciju novaGrupaZaDigitalizaciju = new GrupaZaDigitalizaciju();

        novaGrupaZaDigitalizaciju.setFilmskeTrake(filmskeTrakeNaslovi);
        novaGrupaZaDigitalizaciju.setStatusDigitalizacije(StatusDigitalizacije.NA_DIGITALIZACIJI);
        novaGrupaZaDigitalizaciju.setVrijemePocetka(LocalDateTime.now());
        novaGrupaZaDigitalizaciju.setVrijemeZavrsetka(null);
        novaGrupaZaDigitalizaciju.setVratioUSkladisteKorisnikId(null);
        novaGrupaZaDigitalizaciju.setIznioIzSkladistaKorisnikId(grupaZaDigitalizaciju.getIznioIzSkladistaKorisnikId());
        novaGrupaZaDigitalizaciju = grupaZaDigitalizacijuRepository.save(novaGrupaZaDigitalizaciju);

        //dodavanje id-ja grupe filmovima
        for(String naslov : filmskeTrakeNaslovi){
            FilmskaTraka filmskaTraka = filmskaTrakaRepository.findFilmskaTrakaByNaslov(naslov).get();
            if(filmskaTraka.getGrupeZaDigitalizaciju() != null){
                filmskaTraka.setGrupeZaDigitalizaciju(filmskaTraka.getGrupeZaDigitalizaciju() +";" + novaGrupaZaDigitalizaciju.getIdGrupe().toString());
            } else{
                filmskaTraka.setGrupeZaDigitalizaciju(novaGrupaZaDigitalizaciju.getIdGrupe().toString());
            }
            filmskaTrakaRepository.save(filmskaTraka);
        }

        // Ažuriranje korisnika koji je iznio grupu iz skladišta
        Long korisnikId = grupaZaDigitalizaciju.getIznioIzSkladistaKorisnikId();
        if (korisnikId != null) {
            Optional<Korisnik> korisnikOptional = korisnikRepository.findById(korisnikId);
            if (korisnikOptional.isPresent()) {
                Korisnik korisnik = korisnikOptional.get();

                // Ažuriraj polje iznioIzSkladistaGrupeZaDigitalizaciju
                List<Long> iznioGrupeIds = korisnik.getIznioIzSkladistaGrupeZaDigitalizaciju() != null
                        ? new ArrayList<>(korisnik.getIznioIzSkladistaGrupeZaDigitalizaciju())
                        : new ArrayList<>();

                if (!iznioGrupeIds.contains(novaGrupaZaDigitalizaciju.getIdGrupe())) {
                    iznioGrupeIds.add(novaGrupaZaDigitalizaciju.getIdGrupe());
                }

                korisnik.setIznioIzSkladistaGrupeZaDigitalizaciju(iznioGrupeIds);
                korisnikRepository.save(korisnik);
            } else {
                throw new NoSuchElementException("Korisnik sa ID-jem " + korisnikId + " ne postoji!");
            }
        }

        return novaGrupaZaDigitalizaciju;
    }

    @Override
    public List<GrupaZaDigitalizaciju> getAll() {
        return grupaZaDigitalizacijuRepository.findAll();
    }

    @Override
    public List<String> getFilmsInGroup(Long idGrupe) {
        return grupaZaDigitalizacijuRepository.getFilmsInGroup(idGrupe);
    }

}
