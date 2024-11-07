package hr.unizg.fer.backend.model.primary;

import jakarta.persistence.*;

@Entity
//@Table(name = "FILMSKA_TRAKA")
public class FilmskaTraka {

    @Id
    @Column(name = "IDEmisije", nullable = false)
    private int idEmisije;

    @Column(name = "OriginalniNaslov", nullable = false)
    private int originalniNaslov;

    @Column(name = "RadniNaslov", nullable = false)
    private int radniNaslov;

    @Column(name = "JezikOriginala", nullable = false)
    private int jezikOriginala;

    @Column(name = "Ton", nullable = false)
    private int ton;

    @Column(name = "Emisija", nullable = false)
    private int emisija;

    @Column(name = "Porijeklo_ZemljaProizvodnje", nullable = false)
    private int porijekloZemljaProizvodnje;

    @Column(name = "Licenca", nullable = false)
    private int licenca;

    @Column(name = "GodinaProizvodnje", nullable = false)
    private int godinaProizvodnje;

    @Column(name = "MarkIN", nullable = false)
    private int markIN;

    @Column(name = "MarkOUT", nullable = false)
    private int markOUT;

    @Column(name = "Duration", nullable = false)
    private int duration;

    @Column(name = "BrojMedija", nullable = false)
    private int brojMedija;

    @ManyToOne
    @JoinColumn(name = "IDGrupeZaDigitalizaciju", nullable = false)
    private GrupaZaDigitalizaciju grupaZaDigitalizaciju;

    public FilmskaTraka() {
    }

    public FilmskaTraka(int idEmisije, int originalniNaslov, int radniNaslov, int jezikOriginala,
                        int ton, int emisija, int porijekloZemljaProizvodnje, int licenca,
                        int godinaProizvodnje, int markIN, int markOUT, int duration,
                        int brojMedija, GrupaZaDigitalizaciju grupaZaDigitalizaciju) {
        this.idEmisije = idEmisije;
        this.originalniNaslov = originalniNaslov;
        this.radniNaslov = radniNaslov;
        this.jezikOriginala = jezikOriginala;
        this.ton = ton;
        this.emisija = emisija;
        this.porijekloZemljaProizvodnje = porijekloZemljaProizvodnje;
        this.licenca = licenca;
        this.godinaProizvodnje = godinaProizvodnje;
        this.markIN = markIN;
        this.markOUT = markOUT;
        this.duration = duration;
        this.brojMedija = brojMedija;
        this.grupaZaDigitalizaciju = grupaZaDigitalizaciju;
    }

    public int getIdEmisije() {
        return idEmisije;
    }

    public void setIdEmisije(int idEmisije) {
        this.idEmisije = idEmisije;
    }

    public int getOriginalniNaslov() {
        return originalniNaslov;
    }

    public void setOriginalniNaslov(int originalniNaslov) {
        this.originalniNaslov = originalniNaslov;
    }

    public int getRadniNaslov() {
        return radniNaslov;
    }

    public void setRadniNaslov(int radniNaslov) {
        this.radniNaslov = radniNaslov;
    }

    public int getJezikOriginala() {
        return jezikOriginala;
    }

    public void setJezikOriginala(int jezikOriginala) {
        this.jezikOriginala = jezikOriginala;
    }

    public int getTon() {
        return ton;
    }

    public void setTon(int ton) {
        this.ton = ton;
    }

    public int getEmisija() {
        return emisija;
    }

    public void setEmisija(int emisija) {
        this.emisija = emisija;
    }

    public int getPorijekloZemljaProizvodnje() {
        return porijekloZemljaProizvodnje;
    }

    public void setPorijekloZemljaProizvodnje(int porijekloZemljaProizvodnje) {
        this.porijekloZemljaProizvodnje = porijekloZemljaProizvodnje;
    }

    public int getLicenca() {
        return licenca;
    }

    public void setLicenca(int licenca) {
        this.licenca = licenca;
    }

    public int getGodinaProizvodnje() {
        return godinaProizvodnje;
    }

    public void setGodinaProizvodnje(int godinaProizvodnje) {
        this.godinaProizvodnje = godinaProizvodnje;
    }

    public int getMarkIN() {
        return markIN;
    }

    public void setMarkIN(int markIN) {
        this.markIN = markIN;
    }

    public int getMarkOUT() {
        return markOUT;
    }

    public void setMarkOUT(int markOUT) {
        this.markOUT = markOUT;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getBrojMedija() {
        return brojMedija;
    }

    public void setBrojMedija(int brojMedija) {
        this.brojMedija = brojMedija;
    }

    public GrupaZaDigitalizaciju getGrupaZaDigitalizaciju() {
        return grupaZaDigitalizaciju;
    }

    public void setGrupaZaDigitalizaciju(GrupaZaDigitalizaciju grupaZaDigitalizaciju) {
        this.grupaZaDigitalizaciju = grupaZaDigitalizaciju;
    }
}
