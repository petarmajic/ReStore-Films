package hr.unizg.fer.backend.repository.primary;

import hr.unizg.fer.backend.model.primary.FilmskaTraka;
import hr.unizg.fer.backend.model.primary.GrupaZaDigitalizaciju;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupaZaDigitalizacijuRepository extends JpaRepository<GrupaZaDigitalizaciju, Long> {
    @Query("SELECT g.statusDigitalizacije, COUNT(f) FROM GrupaZaDigitalizaciju g JOIN g.filmskeTrake f GROUP BY g.statusDigitalizacije")
    List<Object[]> countFilmsByStatus();
}
