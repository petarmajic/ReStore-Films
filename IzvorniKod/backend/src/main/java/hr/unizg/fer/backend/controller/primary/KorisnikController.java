package hr.unizg.fer.backend.controller.primary;

import hr.unizg.fer.backend.model.primary.Korisnik;
import hr.unizg.fer.backend.service.primary.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/korisnik")
public class KorisnikController {
    private final KorisnikService korisnikService;

    @Autowired
    public KorisnikController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }

    @GetMapping(path = "/{email}")
    public ResponseEntity<Korisnik> getKorisnikByEmail(@PathVariable String email) {
        Korisnik korisnik = korisnikService.getKorisnikByEmail(email);
        if (korisnik != null) {
            return ResponseEntity.ok(korisnik);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Korisnik> addKorisnik(@RequestBody Korisnik korisnik) {
        try {
            Korisnik savedKorisnik = korisnikService.saveKorisnik(korisnik);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedKorisnik);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());     // ispis na konzolu "GREŠKA"
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}