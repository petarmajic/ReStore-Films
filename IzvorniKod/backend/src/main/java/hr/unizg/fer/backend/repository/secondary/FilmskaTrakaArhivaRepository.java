package hr.unizg.fer.backend.repository.secondary;

import hr.unizg.fer.backend.model.secondary.FilmskaTrakaArhiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmskaTrakaArhivaRepository extends JpaRepository<FilmskaTrakaArhiva, String> {

    @Query(value = "SELECT a FROM FilmskaTrakaArhiva a where a.barKod = :barKod")
    Optional<FilmskaTrakaArhiva> findFilmskaTrakaArhivaByBarKod(String barKod);
}
