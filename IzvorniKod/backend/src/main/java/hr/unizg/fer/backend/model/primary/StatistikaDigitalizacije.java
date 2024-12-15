package hr.unizg.fer.backend.model.primary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "STATISTIKA_DIGITALIZACIJE")
public class StatistikaDigitalizacije {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDStatistike", nullable = false)
    private Long idStatistike;

    @Column(name = "BrojDigitaliziranih")
    private Integer brojDigitaliziranih;

    @Column(name = "BrojNaDigitalizaciji")
    private Integer brojNaDigitalizaciji;

    //naknadno dodano
    /*
    @OneToMany(mappedBy = "statistikaDigitalizacije")
    private Set<Korisnik> korisnici = new HashSet<>();
    */


    @OneToOne(mappedBy = "statistikaDigitalizacije") //nova veza
    @JsonBackReference //za sprečavanje beskonačne petlje
    private Korisnik korisnik;
}