package hr.unizg.fer.backend.model.primary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="filmskatrakaarhiva")
public class FilmskaTraka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDEmisije", nullable = false)
    private Long idEmisije;

    @Column(name = "OriginalniNaslov",nullable = false, unique = true)
    private String originalniNaslov;

    @Column(name = "JezikOriginala", nullable = false)
    private String jezikOriginala;

    @Column(name = "Ton", nullable = false)
    private String ton;

    @Column(name = "VrstaSadrzaja")
    private String vrstaSadrzaja;

    @Column(name = "Porijeklo_ZemljaProizvodnje", nullable = false)
    private String porijekloZemljaProizvodnje;

    @Column(name = "Licenca")
    private String licenca;

    @Column(name = "GodinaProizvodnje", nullable = false)
    private Integer godinaProizvodnje;

    @Column(name = "Duration", nullable = false)
    private LocalTime duration;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Grupiranje",
            joinColumns = @JoinColumn(name="FilmskaTraka_IDEmisije"),
            inverseJoinColumns = @JoinColumn(name="GrupaZaDigitalizaciju_IDGrupe")
    )
    private Set<GrupaZaDigitalizaciju> grupeZaDigitalizaciju = new HashSet<>();
}
