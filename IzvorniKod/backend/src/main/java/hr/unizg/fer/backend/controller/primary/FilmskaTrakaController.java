package hr.unizg.fer.backend.controller.primary;

import hr.unizg.fer.backend.model.primary.FilmskaTraka;
import hr.unizg.fer.backend.model.primary.Korisnik;
import hr.unizg.fer.backend.model.secondary.FilmskaTrakaArhiva;
import hr.unizg.fer.backend.service.primary.FilmskaTrakaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/filmskaTraka")
public class FilmskaTrakaController {

    private final FilmskaTrakaService filmskaTrakaService;

    @Autowired
    public FilmskaTrakaController(FilmskaTrakaService filmskaTrakaService) {
        this.filmskaTrakaService = filmskaTrakaService;
    }
    // Endpoint za dohvat svih filmskih traka
    @GetMapping("/all")
    public ResponseEntity<List<FilmskaTraka>> getAllFilmskeTrake() {
        List<FilmskaTraka> filmskeTrake = filmskaTrakaService.getAllFilmskeTrake();
        return ResponseEntity.ok(filmskeTrake);
    }

    // Endpoint za dohvat filmske trake po ID-u
    @GetMapping("/id/{id}")
    public ResponseEntity<FilmskaTraka> getFilmskaTrakaById(@PathVariable Long id) {
        try {
            FilmskaTraka filmskaTraka = filmskaTrakaService.getFilmskaTrakaById(id);
            return ResponseEntity.ok(filmskaTraka);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Endpoint za dohvat filmske trake po naslovu
    @GetMapping("/naslov/{originalniNaslov}")
    public ResponseEntity<FilmskaTraka> getFilmskaTrakaByNaslov(@PathVariable String originalniNaslov) {
        try {
            FilmskaTraka filmskaTraka = filmskaTrakaService.getFilmskaTrakaByNaslov(originalniNaslov);
            return ResponseEntity.ok(filmskaTraka);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Endpoint za ažuriranje filmske trake
    @PutMapping("/update/{id}")
    public ResponseEntity<FilmskaTraka> updateFilmskaTraka(
            @PathVariable Long id, // ID filmske trake koju ažuriramo
            @RequestBody FilmskaTraka updatedTraka) { // Novi podaci za filmsku traku
        // Pozivamo servisnu metodu za ažuriranje
        try {
            FilmskaTraka updated = filmskaTrakaService.updateFilmskaTraka(id, updatedTraka);
            // Vraćamo ažuriranu filmsku traku
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<FilmskaTraka> createFilmskaTraka(@RequestBody FilmskaTrakaArhiva newTraka) {
        try {
            FilmskaTraka savedFilmskaTraka = filmskaTrakaService.addFilmskaTraka(newTraka);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFilmskaTraka);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());     // ispis na konzolu "GREŠKA"
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
