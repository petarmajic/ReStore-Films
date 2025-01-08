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

    public Map<StatusDigitalizacije, Integer> getFilmCountByStatus() {
        List<Object[]> results = grupaZaDigitalizacijuRepository.countFilmsByStatus();
        Map<StatusDigitalizacije, Integer> statusCounts = new EnumMap<>(StatusDigitalizacije.class);
        for (Object[] result : results) {
            statusCounts.put((StatusDigitalizacije) result[0], ((Number)result[1]).intValue());
        }
        return statusCounts;
    }
    @Override
    public List<Object[]> countGroupsTakenOutByUser() {
        return grupaZaDigitalizacijuRepository.countGroupsTakenOutByUser();
    }

    @Override
    public List<Object[]> countGroupsReturnedByUser() {
        return grupaZaDigitalizacijuRepository.countGroupsReturnedByUser();
    }

    @Override
    public GrupaZaDigitalizaciju addFilms(List<String> nasloviFilmova, GrupaZaDigitalizaciju grupaZaDigitalizaciju) {
        List<String> filmskeTrakeNaslovi = new ArrayList<>();
        for(String naslov : nasloviFilmova) {
            if(filmskaTrakaRepository.findFilmskaTrakaByNaslov(naslov).isPresent()){
                filmskeTrakeNaslovi.add(naslov);
            }
            else{
                throw new NoSuchElementException("Ne postojeći film u bazi imena: " + naslov + " !");
            }
        }

        grupaZaDigitalizaciju.setFilmskeTrake(filmskeTrakeNaslovi);
        return grupaZaDigitalizacijuRepository.save(grupaZaDigitalizaciju);
    }

    @Override
    public List<GrupaZaDigitalizaciju> getAll() {
        return grupaZaDigitalizacijuRepository.findAll();
    }

//    @Override
//    public GrupaZaDigitalizaciju updateGroup(Long idGrupe, String emailKorisnika) {
//        //pronadi grupu
//        GrupaZaDigitalizaciju grupa = grupaZaDigitalizacijuRepository.findById(idGrupe)
//                .orElseThrow(() -> new NoSuchElementException("Grupa s ID-em " + idGrupe + " nije pronađena."));
//
//        //pronadi korisnika
//        Korisnik korisnik = korisnikRepository.findKorisnikByEmail(emailKorisnika)
//                .orElseThrow(() -> new NoSuchElementException("Korisnik s emailom " + emailKorisnika + " nije pronađen."));
//
//        //azuriraj atribute
//        grupa.setStatusDigitalizacije(StatusDigitalizacije.ZAVRSENO);
//        grupa.setVrijemeZavrsetka(LocalDateTime.now());
//        grupa.setVratioUSkladisteKorisnikId(korisnik.getIdKorisnika());
//
//        //sacuvaj promjene
//        return grupaZaDigitalizacijuRepository.save(grupa);
//    }


}
