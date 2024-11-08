package hr.unizg.fer.backend.model.secondary;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;

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

    public FilmskaTrakaArhiva() {
    }

    public FilmskaTrakaArhiva(String barKod, String originalniNaslov, String radniNaslov, String jezikOriginala, String ton, String emisija, String vrstaSadrzaja, String porijekloZemljaProizvodnje, String licenca, Integer godinaProizvodnje, LocalTime markIN, LocalTime markOUT, LocalTime duration, String brojMedija) {
        this.barKod = barKod;
        this.originalniNaslov = originalniNaslov;
        this.radniNaslov = radniNaslov;
        this.jezikOriginala = jezikOriginala;
        this.ton = ton;
        this.emisija = emisija;
        this.vrstaSadrzaja = vrstaSadrzaja;
        this.porijekloZemljaProizvodnje = porijekloZemljaProizvodnje;
        this.licenca = licenca;
        this.godinaProizvodnje = godinaProizvodnje;
        this.markIN = markIN;
        this.markOUT = markOUT;
        this.duration = duration;
        this.brojMedija = brojMedija;
    }

    public String getBarKod() {
        return barKod;
    }

    public void setBarKod(String barKod) {
        this.barKod = barKod;
    }

    public String getOriginalniNaslov() {
        return originalniNaslov;
    }

    public void setOriginalniNaslov(String originalniNaslov) {
        this.originalniNaslov = originalniNaslov;
    }

    public String getRadniNaslov() {
        return radniNaslov;
    }

    public void setRadniNaslov(String radniNaslov) {
        this.radniNaslov = radniNaslov;
    }

    public String getJezikOriginala() {
        return jezikOriginala;
    }

    public void setJezikOriginala(String jezikOriginala) {
        this.jezikOriginala = jezikOriginala;
    }

    public String getTon() {
        return ton;
    }

    public void setTon(String ton) {
        this.ton = ton;
    }

    public String getEmisija() {
        return emisija;
    }

    public void setEmisija(String emisija) {
        this.emisija = emisija;
    }

    public String getVrstaSadrzaja() {
        return vrstaSadrzaja;
    }

    public void setVrstaSadrzaja(String vrstaSadrzaja) {
        this.vrstaSadrzaja = vrstaSadrzaja;
    }

    public String getPorijekloZemljaProizvodnje() {
        return porijekloZemljaProizvodnje;
    }

    public void setPorijekloZemljaProizvodnje(String porijekloZemljaProizvodnje) {
        this.porijekloZemljaProizvodnje = porijekloZemljaProizvodnje;
    }

    public String getLicenca() {
        return licenca;
    }

    public void setLicenca(String licenca) {
        this.licenca = licenca;
    }

    public Integer getGodinaProizvodnje() {
        return godinaProizvodnje;
    }

    public void setGodinaProizvodnje(Integer godinaProizvodnje) {
        this.godinaProizvodnje = godinaProizvodnje;
    }

    public LocalTime getMarkIN() {
        return markIN;
    }

    public void setMarkIN(LocalTime markIN) {
        this.markIN = markIN;
    }

    public LocalTime getMarkOUT() {
        return markOUT;
    }

    public void setMarkOUT(LocalTime markOUT) {
        this.markOUT = markOUT;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public String getBrojMedija() {
        return brojMedija;
    }

    public void setBrojMedija(String brojMedija) {
        this.brojMedija = brojMedija;
    }
}
