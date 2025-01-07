package hr.unizg.fer.backend.repository.primary;

import hr.unizg.fer.backend.model.primary.GrupaZaDigitalizaciju;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupaZaDigitalizacijuRepository extends JpaRepository<GrupaZaDigitalizaciju, Long> {

    @Query("SELECT g.statusDigitalizacije, COUNT(f) FROM GrupaZaDigitalizaciju g JOIN g.filmskeTrake f GROUP BY g.statusDigitalizacije")
    List<Object[]> countFilmsByStatus();

    //koliko je grupa korisnik iznio iz skladista
    @Query("SELECT k.idKorisnika, COUNT(g) FROM Korisnik k JOIN k.iznioIzSkladistaGrupeZaDigitalizaciju g GROUP BY k.idKorisnika")
    List<Object[]> countGroupsTakenOutByUser();

    //koliko je grupa korisnik vratio u skladiste
    @Query("SELECT k.idKorisnika, COUNT(g) FROM Korisnik k JOIN k.vratioUSkladisteGrupeZaDigitalizaciju g GROUP BY k.idKorisnika")
    List<Object[]> countGroupsReturnedByUser();

}
