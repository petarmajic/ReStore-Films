package hr.unizg.fer.backend.model.primary;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

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
    private Korisnik korisnik;

    @ManyToOne
    @JoinColumn(name = "IDDjelatnikaVratio", nullable = false)
    private Korisnik vratioUSkladisteKorisnik;

    @OneToMany(mappedBy = "grupaZaDigitalizaciju")
    private Set<FilmskaTraka> filmskeTrake = new HashSet<>();


    public GrupaZaDigitalizaciju() {
    }

    public GrupaZaDigitalizaciju(Long idGrupe, StatusDigitalizacije statusDigitalizacije, LocalDateTime vrijemePocetka, LocalDateTime vrijemeZavrsetka, Korisnik korisnik, Korisnik vratioUSkladisteKorisnik, Set<FilmskaTraka> filmskeTrake) {
        this.idGrupe = idGrupe;
        this.statusDigitalizacije = statusDigitalizacije;
        this.vrijemePocetka = vrijemePocetka;
        this.vrijemeZavrsetka = vrijemeZavrsetka;
        this.korisnik = korisnik;
        this.vratioUSkladisteKorisnik = vratioUSkladisteKorisnik;
        this.filmskeTrake = filmskeTrake;
    }

    public Long getIdGrupe() {
        return idGrupe;
    }

    public void setIdGrupe(Long idGrupe) {
        this.idGrupe = idGrupe;
    }

    public StatusDigitalizacije getStatusDigitalizacije() {
        return statusDigitalizacije;
    }

    public void setStatusDigitalizacije(StatusDigitalizacije statusDigitalizacije) {
        this.statusDigitalizacije = statusDigitalizacije;
    }

    public LocalDateTime getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(LocalDateTime vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }

    public LocalDateTime getVrijemeZavrsetka() {
        return vrijemeZavrsetka;
    }

    public void setVrijemeZavrsetka(LocalDateTime vrijemeZavrsetka) {
        this.vrijemeZavrsetka = vrijemeZavrsetka;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Korisnik getVratioUSkladisteKorisnik() {
        return vratioUSkladisteKorisnik;
    }

    public void setVratioUSkladisteKorisnik(Korisnik vratioUSkladisteKorisnik) {
        this.vratioUSkladisteKorisnik = vratioUSkladisteKorisnik;
    }

    public Set<FilmskaTraka> getFilmskeTrake() {
        return filmskeTrake;
    }

    public void setFilmskeTrake(Set<FilmskaTraka> filmskeTrake) {
        this.filmskeTrake = filmskeTrake;
    }
}
