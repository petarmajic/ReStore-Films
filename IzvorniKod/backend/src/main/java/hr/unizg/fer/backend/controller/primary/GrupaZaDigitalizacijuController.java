package hr.unizg.fer.backend.controller.primary;

import hr.unizg.fer.backend.model.primary.StatusDigitalizacije;
import hr.unizg.fer.backend.service.primary.GrupaZaDigitalizacijuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grupaZaDigitalizaciju")
public class GrupaZaDigitalizacijuController {
    private final GrupaZaDigitalizacijuService service;

    @Autowired
    public GrupaZaDigitalizacijuController(GrupaZaDigitalizacijuService service) {
        this.service = service;
    }

    @GetMapping("/statusCounts")
    public ResponseEntity<Map<StatusDigitalizacije, Integer>> getStatusCounts() {
        return ResponseEntity.ok(service.getFilmCountByStatus());
    }

    @GetMapping("/groupsOut")
    public List<Object[]> getGroupsTakenOutStatistics() {
        return service.countGroupsTakenOutByUser();
    }

    @GetMapping("/groupsReturned")
    public List<Object[]> getGroupsReturnedStatistics() {
        return service.countGroupsReturnedByUser();
    }
}
