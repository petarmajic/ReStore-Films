package hr.unizg.fer.backend.model.primary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GRUPA_ZA_DIGITALIZACIJU")
public class GrupaZaDigitalizaciju {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "IDGrupe", nullable = false)
    private Long idGrupe;

    @Column(name = "StatusDigitalizacije", nullable = false)
    private StatusDigitalizacije statusDigitalizacije;

    @Column(name = "VrijemePocetka", nullable = false)
    private LocalDateTime vrijemePocetka;

    @Column(name = "VrijemeZavrsetka", nullable = false)
    private LocalDateTime vrijemeZavrsetka;

    @ManyToOne
    @JoinColumn(name = "IDDjelatnikaIznio", nullable = false)
    private Korisnik iznioIzSkladistaKorisnik;

    @ManyToOne
    @JoinColumn(name = "IDDjelatnikaVratio", nullable = false)
    private Korisnik vratioUSkladisteKorisnik;

    @OneToMany(mappedBy = "grupaZaDigitalizaciju")
    private Set<FilmskaTraka> filmskeTrake = new HashSet<>();

}
