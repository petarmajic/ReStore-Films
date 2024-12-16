package hr.unizg.fer.backend.repository.primary;

import hr.unizg.fer.backend.model.primary.FilmskaTraka;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FilmskaTrakaRepository extends JpaRepository<FilmskaTraka, Long> {
//    // Pronalazi sve FilmskeTrake
//    @Query("SELECT f FROM FilmskaTraka f")
//    List<FilmskaTraka> findAllFilmskeTrake();

    // Pronalazi FilmskuTraku po ID-u
    @Query("SELECT f FROM FilmskaTraka f WHERE f.idEmisije = :id")
    FilmskaTraka findFilmskaTrakaById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE FilmskaTraka f SET " +
            "f.originalniNaslov = :originalniNaslov, " +
            "f.radniNaslov = :radniNaslov, " +
            "f.jezikOriginala = :jezikOriginala, " +
            "f.ton = :ton, " +
            "f.emisija = :emisija, " +
            "f.porijekloZemljaProizvodnje = :porijekloZemljaProizvodnje, " +
            "f.licenca = :licenca, " +
            "f.godinaProizvodnje = :godinaProizvodnje, " +
            "f.markIN = :markIN, " +
            "f.markOUT = :markOUT, " +
            "f.duration = :duration, " +
            "f.brojMedija = :brojMedija, " +
            "f.vrstaSadrzaja = :vrstaSadrzaja " +
            "WHERE f.idEmisije = :idEmisije")
    void updateFilmskaTraka(Long idEmisije, String originalniNaslov, String radniNaslov,
                            String jezikOriginala, String ton, String emisija,
                            String porijekloZemljaProizvodnje, String licenca,
                            Integer godinaProizvodnje, String markIN, String markOUT,
                            Double duration, Integer brojMedija, String vrstaSadrzaja);
}
