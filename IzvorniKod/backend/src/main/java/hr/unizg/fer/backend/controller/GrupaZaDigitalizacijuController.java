package hr.unizg.fer.backend.controller;

import hr.unizg.fer.backend.model.primary.StatusDigitalizacije;
import hr.unizg.fer.backend.service.GrupaZaDigitalizacijuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/digitalizacija")
public class GrupaZaDigitalizacijuController {
    private final GrupaZaDigitalizacijuService service;

    @Autowired
    public GrupaZaDigitalizacijuController(GrupaZaDigitalizacijuService service) {
        this.service = service;
    }

    @GetMapping("/status-counts")
    public ResponseEntity<Map<StatusDigitalizacije, Long>> getStatusCounts() {
        return ResponseEntity.ok(service.getFilmCountByStatus());
    }
}
