package hr.unizg.fer.backend.service.primary.impl;

import hr.unizg.fer.backend.model.primary.FilmskaTraka;
import hr.unizg.fer.backend.repository.primary.FilmskaTrakaRepository;
import hr.unizg.fer.backend.service.primary.FilmskaTrakaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmskaTrakaServiceImpl implements FilmskaTrakaService {

    private final FilmskaTrakaRepository filmskaTrakaRepository;

    @Autowired
    public FilmskaTrakaServiceImpl(FilmskaTrakaRepository filmskaTrakaRepository) {
        this.filmskaTrakaRepository = filmskaTrakaRepository;
    }

    @Override
    public List<FilmskaTraka> getAllFilmskeTrake() {
        return filmskaTrakaRepository.findAll();
    }

    @Override
    public FilmskaTraka getFilmskaTrakaById(Long id) {
        return filmskaTrakaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filmska traka s ID-om " + id + " ne postoji."));
    }

    @Override
    public FilmskaTraka updateFilmskaTraka(Long id, FilmskaTraka updatedTraka) {
        // Provjera postoji li filmska traka s tim id-em
        FilmskaTraka existingTraka = filmskaTrakaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Filmska traka s ID-om " + id + " ne postoji."));

        // Ažuriranje polja
        if (updatedTraka.getOriginalniNaslov() != null) existingTraka.setOriginalniNaslov(updatedTraka.getOriginalniNaslov());
        if (updatedTraka.getRadniNaslov() != null) existingTraka.setRadniNaslov(updatedTraka.getRadniNaslov());
        if (updatedTraka.getJezikOriginala() != null) existingTraka.setJezikOriginala(updatedTraka.getJezikOriginala());
        if (updatedTraka.getTon() != null) existingTraka.setTon(updatedTraka.getTon());
        if (updatedTraka.getEmisija() != null) existingTraka.setEmisija(updatedTraka.getEmisija());
        if (updatedTraka.getPorijekloZemljaProizvodnje() != null) existingTraka.setPorijekloZemljaProizvodnje(updatedTraka.getPorijekloZemljaProizvodnje());
        if (updatedTraka.getLicenca() != null) existingTraka.setLicenca(updatedTraka.getLicenca());
        if (updatedTraka.getGodinaProizvodnje() != null) existingTraka.setGodinaProizvodnje(updatedTraka.getGodinaProizvodnje());
        if (updatedTraka.getMarkIN() != null) existingTraka.setMarkIN(updatedTraka.getMarkIN());
        if (updatedTraka.getMarkOUT() != null) existingTraka.setMarkOUT(updatedTraka.getMarkOUT());
        if (updatedTraka.getDuration() != null) existingTraka.setDuration(updatedTraka.getDuration());
        if (updatedTraka.getBrojMedija() != null) existingTraka.setBrojMedija(updatedTraka.getBrojMedija());
        if (updatedTraka.getVrstaSadrzaja() != null) existingTraka.setVrstaSadrzaja(updatedTraka.getVrstaSadrzaja());

        // Spremanje ažurirane filmske trake u bazu podataka
        return filmskaTrakaRepository.save(existingTraka);
    }
}
