package hr.unizg.fer.backend.model.primary;

import jakarta.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "IDStatistike")
    private StatistikaDigitalizacije statistikaDigitalizacije;

    public Korisnik() {
    }

    public Korisnik(Long idKorisnika, String ime, String prezime, String korisnickoIme, String email, String lozinka, UlogaKorisnika uloga, StatistikaDigitalizacije statistikaDigitalizacije) {
        this.idKorisnika = idKorisnika;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.email = email;
        this.lozinka = lozinka;
        this.uloga = uloga;
        this.statistikaDigitalizacije = statistikaDigitalizacije;
    }

    public Long getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(Long idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public UlogaKorisnika getUloga() {
        return uloga;
    }

    public void setUloga(UlogaKorisnika uloga) {
        this.uloga = uloga;
    }

    public StatistikaDigitalizacije getStatistikaDigitalizacije() {
        return statistikaDigitalizacije;
    }

    public void setStatistikaDigitalizacije(StatistikaDigitalizacije statistikaDigitalizacije) {
        this.statistikaDigitalizacije = statistikaDigitalizacije;
    }
}

