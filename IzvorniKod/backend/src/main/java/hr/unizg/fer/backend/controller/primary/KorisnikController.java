package hr.unizg.fer.backend.controller.primary;

import hr.unizg.fer.backend.model.primary.Korisnik;
import hr.unizg.fer.backend.service.primary.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
