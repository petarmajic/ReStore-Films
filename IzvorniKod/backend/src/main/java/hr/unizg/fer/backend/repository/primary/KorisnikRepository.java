package hr.unizg.fer.backend.repository.primary;

import hr.unizg.fer.backend.model.primary.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

    @Query(value = "SELECT a FROM Korisnik a where a.email = :email")
    Optional<Korisnik> findKorisnikByEmail(String email);
}
