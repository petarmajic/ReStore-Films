package hr.unizg.fer.backend.model.primary;

import jakarta.persistence.*;

@Entity
@Table(name = "KORISNIK")
public class Korisnik {

    @Id
    @Column(name = "IDKorisnika", nullable = false)
    private int idKorisnika;

    @Column(name = "Ime", nullable = false)
    private int ime;

    @Column(name = "Prezime", nullable = false)
    private int prezime;

    @Column(name = "KorisnickoIme", nullable = false)
    private int korisnickoIme;

    @Column(name = "Email", nullable = false)
    private int email;

    @Column(name = "Lozinka", nullable = false)
    private int lozinka;

    @Column(name = "Uloga", nullable = false)
    private int uloga;

    @ManyToOne
    @JoinColumn(name = "IDStatistike")
    private StatistikaDigitalizacije statistikaDigitalizacije;

    public Korisnik() {
    }

    public Korisnik(int idKorisnika, int ime, int prezime, int korisnickoIme, int email, int lozinka, int uloga, StatistikaDigitalizacije statistikaDigitalizacije) {
        this.idKorisnika = idKorisnika;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.email = email;
        this.lozinka = lozinka;
        this.uloga = uloga;
        this.statistikaDigitalizacije = statistikaDigitalizacije;
    }

    public int getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(int idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public int getIme() {
        return ime;
    }

    public void setIme(int ime) {
        this.ime = ime;
    }

    public int getPrezime() {
        return prezime;
    }

    public void setPrezime(int prezime) {
        this.prezime = prezime;
    }

    public int getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(int korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public int getEmail() {
        return email;
    }

    public void setEmail(int email) {
        this.email = email;
    }

    public int getLozinka() {
        return lozinka;
    }

    public void setLozinka(int lozinka) {
        this.lozinka = lozinka;
    }

    public int getUloga() {
        return uloga;
    }

    public void setUloga(int uloga) {
        this.uloga = uloga;
    }

    public StatistikaDigitalizacije getStatistikaDigitalizacije() {
        return statistikaDigitalizacije;
    }

    public void setStatistikaDigitalizacije(StatistikaDigitalizacije statistikaDigitalizacije) {
        this.statistikaDigitalizacije = statistikaDigitalizacije;
    }
}

