package hr.unizg.fer.backend.repository.primary;

import hr.unizg.fer.backend.model.primary.GrupaZaDigitalizaciju;
import hr.unizg.fer.backend.model.primary.StatusDigitalizacije;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupaZaDigitalizacijuRepository extends JpaRepository<GrupaZaDigitalizaciju, Long> {

    @Query(value = "SELECT COUNT(*) FROM grupa_za_digitalizaciju WHERE statusdigitalizacije= :statusDigitalizacije", nativeQuery = true)
    Long countFilmsByStatus(StatusDigitalizacije statusDigitalizacije);

    //koliko je grupa korisnik iznio iz skladista
    @Query(value = "SELECT COUNT(*) FROM grupa_za_digitalizaciju WHERE iznioizskladistakorisnikid= :idKorisnika", nativeQuery = true)
    Long countGroupsTakenOutByUser(Long idKorisnika);

    //koliko je grupa korisnik vratio u skladiste
    @Query(value = "SELECT COUNT(*) FROM grupa_za_digitalizaciju WHERE vratiouskladistekorisnikid = :idKorisnika", nativeQuery = true)
    Long countGroupsReturnedByUser(Long idKorisnika);

    @Query(value = "SELECT filmskatraka_originalninaslov FROM grupa_filmskeTrake WHERE idgrupazadigitalizaciju = :idGrupe", nativeQuery = true)
    List<String> getFilmsInGroup(Long idGrupe);

}
