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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @Column(name = "VrijemeZavrsetka")
    private LocalDateTime vrijemeZavrsetka;

    @Column(name = "iznioIzSkladistaKorisnikId")
    private Long iznioIzSkladistaKorisnikId;

    @Column(name = "vratioUSkladisteKorisnikId")
    private Long vratioUSkladisteKorisnikId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "FilmskaTraka_Grupe", joinColumns = @JoinColumn(name = "idGrupaZaDigitalizaciju"))
    @Column(name = "filmskaTraka_originalniNaslov")
    private List<String> filmskeTrake = new ArrayList<>();




}
