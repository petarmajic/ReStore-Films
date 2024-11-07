package hr.unizg.fer.backend.repository.secondary;

import hr.unizg.fer.backend.model.secondary.FilmskaTrakaArhiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmskaTrakaArhivaRepository extends JpaRepository<FilmskaTrakaArhiva, Integer> { }
