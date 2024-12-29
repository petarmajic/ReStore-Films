package hr.unizg.fer.backend.repository.primary;

import hr.unizg.fer.backend.model.primary.FilmskaTraka;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmskaTrakaRepository extends JpaRepository<FilmskaTraka, Long> {

    @Query("SELECT f FROM FilmskaTraka f WHERE f.originalniNaslov = :naslov")
    Optional<FilmskaTraka> findFilmskaTrakaByNaslov(@Param("naslov") String naslov);

    @Modifying
    @Transactional
    @Query("UPDATE FilmskaTraka f SET " +
            "f.originalniNaslov = :originalniNaslov, " +
            "f.jezikOriginala = :jezikOriginala, " +
            "f.ton = :ton, " +
            "f.porijekloZemljaProizvodnje = :porijekloZemljaProizvodnje, " +
            "f.licenca = :licenca, " +
            "f.godinaProizvodnje = :godinaProizvodnje, " +
            "f.duration = :duration, " +
            "f.vrstaSadrzaja = :vrstaSadrzaja " +
            "WHERE f.idEmisije = :idEmisije")
    void updateFilmskaTraka(Long idEmisije, String originalniNaslov,
                            String jezikOriginala, String ton,
                            String porijekloZemljaProizvodnje, String licenca,
                            Integer godinaProizvodnje,
                            Double duration, String vrstaSadrzaja);
}
