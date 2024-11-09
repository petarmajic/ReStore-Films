package hr.unizg.fer.backend.model.secondary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FilmskaTrakaArhiva {

    @Id
    @Column(name = "BarKod", nullable = false)
    private String barKod;

    @Column(name = "OriginalniNaslov",nullable = false)
    private String originalniNaslov;

    @Column(name = "RadniNaslov", nullable = true)
    private String radniNaslov;

    @Column(name = "JezikOriginala", nullable = false)
    private String jezikOriginala;

    @Column(name = "Ton", nullable = false)
    private String ton;

    @Column(name = "Emisija", nullable = true)
    private String emisija;

    @Column(name = "VrstaSadrzaja")
    private String vrstaSadrzaja;

    @Column(name = "Porijeklo_ZemljaProizvodnje", nullable = false)
    private String porijekloZemljaProizvodnje;

    @Column(name = "Licenca", nullable = true)
    private String licenca;

    @Column(name = "GodinaProizvodnje", nullable = false)
    private Integer godinaProizvodnje;

    @Column(name = "MarkIN", nullable = false)
    private LocalTime markIN;

    @Column(name = "MarkOUT", nullable = false)
    private LocalTime markOUT;

    @Column(name = "Duration", nullable = false)
    private LocalTime duration;

    @Column(name = "BrojMedija", nullable = false)
    private String brojMedija;

}
