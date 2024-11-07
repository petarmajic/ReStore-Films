package hr.unizg.fer.backend.model.primary;

import jakarta.persistence.*;

@Entity
@Table(name = "grupiranje")
public class Grupiranje {

    @Id
    @ManyToOne
    @JoinColumn(name = "IDEmisije", nullable = false)
    private FilmskaTraka filmskaTraka;

    @Id
    @ManyToOne
    @JoinColumn(name = "IDGrupe", nullable = false)
    private GrupaZaDigitalizaciju grupaZaDigitalizaciju;

    public Grupiranje() {
    }

    public Grupiranje(FilmskaTraka filmskaTraka, GrupaZaDigitalizaciju grupaZaDigitalizaciju) {
        this.filmskaTraka = filmskaTraka;
        this.grupaZaDigitalizaciju = grupaZaDigitalizaciju;
    }

    public FilmskaTraka getFilmskaTraka() {
        return filmskaTraka;
    }

    public void setFilmskaTraka(FilmskaTraka filmskaTraka) {
        this.filmskaTraka = filmskaTraka;
    }

    public GrupaZaDigitalizaciju getGrupaZaDigitalizaciju() {
        return grupaZaDigitalizaciju;
    }

    public void setGrupaZaDigitalizaciju(GrupaZaDigitalizaciju grupaZaDigitalizaciju) {
        this.grupaZaDigitalizaciju = grupaZaDigitalizaciju;
    }
}

