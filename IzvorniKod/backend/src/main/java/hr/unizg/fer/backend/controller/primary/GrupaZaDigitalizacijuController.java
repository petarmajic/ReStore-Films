package hr.unizg.fer.backend.controller.primary;

import hr.unizg.fer.backend.dto.GrupaZaDigitalizacijuRequest;
import hr.unizg.fer.backend.model.primary.FilmskaTraka;
import hr.unizg.fer.backend.model.primary.GrupaZaDigitalizaciju;
import hr.unizg.fer.backend.model.primary.StatusDigitalizacije;
import hr.unizg.fer.backend.service.primary.GrupaZaDigitalizacijuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/api/grupaZaDigitalizaciju")
public class GrupaZaDigitalizacijuController {
    private final GrupaZaDigitalizacijuService grupaZaDigitalizacijuService;

    @Autowired
    public GrupaZaDigitalizacijuController(GrupaZaDigitalizacijuService grupaZaDigitalizacijuService) {
        this.grupaZaDigitalizacijuService = grupaZaDigitalizacijuService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<GrupaZaDigitalizaciju>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(grupaZaDigitalizacijuService.getAll());
    }

    @GetMapping("/statusCounts/{statusDigitalizacije}")
    public Long getStatusCounts(@PathVariable StatusDigitalizacije statusDigitalizacije) {
        return grupaZaDigitalizacijuService.getFilmCountByStatus(statusDigitalizacije);
    }

    @GetMapping("/groupsOut/{idKorisnika}")
    public Long getGroupsTakenOutStatistics(@PathVariable Long idKorisnika) {
        return grupaZaDigitalizacijuService.countGroupsTakenOutByUser(idKorisnika);
    }

    @GetMapping("/groupsReturned/{idKorisnika}")
    public Long getGroupsReturnedStatistics(@PathVariable Long idKorisnika) {
        return grupaZaDigitalizacijuService.countGroupsReturnedByUser(idKorisnika);
    }

    //provjerit ko može slat na digitalizaciju pa ako treba radit provjeru
    // u tijelu dobijam naslove filmova koje zelim dodati u grupu za digitalizaciju
    @PostMapping(path = "/add")
    public ResponseEntity<GrupaZaDigitalizaciju> createGroup(@RequestBody GrupaZaDigitalizacijuRequest
                                                                         grupaZaDigitalizacijuRequest) {
        try{
            GrupaZaDigitalizaciju updatedGrupaZaDigitalizaciju = grupaZaDigitalizacijuService.addFilms(
                    grupaZaDigitalizacijuRequest.getNasloviFilmova(),
                    grupaZaDigitalizacijuRequest.getGrupaZaDigitalizaciju());
            return ResponseEntity.status(HttpStatus.OK).body(updatedGrupaZaDigitalizaciju);
        } catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

//    @PatchMapping("/{id}/updateGroup")
//    public ResponseEntity<GrupaZaDigitalizaciju> updateGroup(
//            @PathVariable Long id,
//            @RequestParam String userEmail) {
//        try {
//            GrupaZaDigitalizaciju updatedGroup = grupaZaDigitalizacijuService.updateGroup(id, userEmail);
//            return ResponseEntity.status(HttpStatus.OK).body(updatedGroup);
//        } catch (NoSuchElementException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }

}
