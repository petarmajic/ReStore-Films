package hr.unizg.fer.backend.model.primary;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "KORISNIK")
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDKorisnika", nullable = false)
    private Long idKorisnika;

    @Column(name = "Ime", nullable = false)
    private String ime;

    @Column(name = "Prezime", nullable = false)
    private String prezime;

    @Column(name = "KorisnickoIme", nullable = false)
    private String korisnickoIme;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Lozinka", nullable = false)
    private String lozinka;

    @Column(name = "Uloga", nullable = false)
    private UlogaKorisnika uloga;

    /*
    @ManyToOne
    @JoinColumn(name = "IDStatistike", nullable = false)
    private StatistikaDigitalizacije statistikaDigitalizacije;
     */

    @OneToOne(cascade = CascadeType.ALL) // cascadeType.ALL da se u bazi automatski handla i stvaranje, brisanje.. staDig
    @JoinColumn(name = "IDStatistike", nullable = false)
    @JsonManagedReference // za sprječavanje beskonačne petlje
    private StatistikaDigitalizacije statistikaDigitalizacije;

    @OneToMany(mappedBy = "iznioIzSkladistaKorisnik", fetch = FetchType.EAGER)
    private Set<GrupaZaDigitalizaciju> iznioIzSkladistaGrupeZaDigitalizaciju = new HashSet<>();

    @OneToMany(mappedBy = "vratioUSkladisteKorisnik", fetch = FetchType.EAGER)
    private Set<GrupaZaDigitalizaciju> vratioUSkladisteGrupeZaDigitalizaciju = new HashSet<>();

}

