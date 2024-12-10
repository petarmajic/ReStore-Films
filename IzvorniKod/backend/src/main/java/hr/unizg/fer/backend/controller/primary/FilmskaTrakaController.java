package hr.unizg.fer.backend.controller.primary;

import hr.unizg.fer.backend.model.primary.FilmskaTraka;
import hr.unizg.fer.backend.service.primary.FilmskaTrakaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/{id}")
    public ResponseEntity<FilmskaTraka> getFilmskaTrakaById(@PathVariable Long id) {
        FilmskaTraka filmskaTraka = filmskaTrakaService.getFilmskaTrakaById(id);
        return ResponseEntity.ok(filmskaTraka);
    }

    // Endpoint za ažuriranje filmske trake
    @PutMapping("/{id}")
    public ResponseEntity<FilmskaTraka> updateFilmskaTraka(
            @PathVariable Long id, // ID filmske trake koju ažuriramo
            @RequestBody FilmskaTraka updatedTraka) { // Novi podaci za filmsku traku
        // Pozivamo servisnu metodu za ažuriranje
        FilmskaTraka updated = filmskaTrakaService.updateFilmskaTraka(id, updatedTraka);

        // Vraćamo ažuriranu filmsku traku
        return ResponseEntity.ok(updated);
    }

}
