package hr.unizg.fer.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "GRUPA_ZA_DIGITALIZACIJU")
public class GrupaZaDigitalizaciju {

    @Id
    @Column(name = "IDGrupe", nullable = false)
    private int idGrupe;

    @Column(name = "StatusDigitalizacije", nullable = false)
    private int statusDigitalizacije;

    @Column(name = "VrijemePocetka", nullable = false)
    private int vrijemePocetka;

    @Column(name = "VrijemeZavrsetka", nullable = false)
    private int vrijemeZavrsetka;

    @ManyToOne
    @JoinColumn(name = "IDKorisnika", nullable = false)
    private Korisnik korisnik;

    @ManyToOne
    @JoinColumn(name = "vratioUSkladisteIDKorisnika", nullable = false)
    private Korisnik vratioUSkladisteKorisnik;

    public GrupaZaDigitalizaciju() {
    }

    public GrupaZaDigitalizaciju(int idGrupe, int statusDigitalizacije, int vrijemePocetka, int vrijemeZavrsetka, Korisnik korisnik, Korisnik vratioUSkladisteKorisnik) {
        this.idGrupe = idGrupe;
        this.statusDigitalizacije = statusDigitalizacije;
        this.vrijemePocetka = vrijemePocetka;
        this.vrijemeZavrsetka = vrijemeZavrsetka;
        this.korisnik = korisnik;
        this.vratioUSkladisteKorisnik = vratioUSkladisteKorisnik;
    }

    public int getIdGrupe() {
        return idGrupe;
    }

    public void setIdGrupe(int idGrupe) {
        this.idGrupe = idGrupe;
    }

    public int getStatusDigitalizacije() {
        return statusDigitalizacije;
    }

    public void setStatusDigitalizacije(int statusDigitalizacije) {
        this.statusDigitalizacije = statusDigitalizacije;
    }

    public int getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(int vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }

    public int getVrijemeZavrsetka() {
        return vrijemeZavrsetka;
    }

    public void setVrijemeZavrsetka(int vrijemeZavrsetka) {
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
}
