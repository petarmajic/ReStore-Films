package hr.unizg.fer.backend.model.primary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference("iznioReference")
    private Korisnik iznioIzSkladistaKorisnik;

    @ManyToOne
    @JoinColumn(name = "IDDjelatnikaVratio", nullable = false)
    @JsonBackReference("vratioReference")
    private Korisnik vratioUSkladisteKorisnik;

    //provjerit dal je glavno ova klasa ili filmskaTraka pa oviso o tome okrenut JsonManagedReference i JsonBackReference
    @ManyToMany(mappedBy = "grupeZaDigitalizaciju")
    @JsonIgnore //ako ne bude radilo maknut
    private Set<FilmskaTraka> filmskeTrake;




}
