package hr.unizg.fer.backend.controller.secondary;

import hr.unizg.fer.backend.model.secondary.FilmskaTrakaArhiva;
import hr.unizg.fer.backend.service.secondary.FilmskaTrakaArhivaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/filmskaTrakaArhiva")
public class FilmskaTrakaArhivaController {

    private final FilmskaTrakaArhivaService filmskaTrakaArhivaService;

    @Autowired
    public FilmskaTrakaArhivaController(FilmskaTrakaArhivaService filmskaTrakaArhivaService) {
        this.filmskaTrakaArhivaService = filmskaTrakaArhivaService;
    }


    @GetMapping("/{barKod}")
    public ResponseEntity<FilmskaTrakaArhiva> getFilmskaTrakaByBarKod(@PathVariable String barKod) {
        FilmskaTrakaArhiva filmskaTrakaArhiva = filmskaTrakaArhivaService.getFilmskaTrakaArhivaByBarKod(barKod);
        if(filmskaTrakaArhiva != null) {
            return ResponseEntity.ok(filmskaTrakaArhiva);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
