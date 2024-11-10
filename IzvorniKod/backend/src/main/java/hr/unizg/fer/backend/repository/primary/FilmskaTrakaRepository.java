package hr.unizg.fer.backend.repository.primary;

import hr.unizg.fer.backend.model.primary.FilmskaTraka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmskaTrakaRepository extends JpaRepository<FilmskaTraka, Long> { }
