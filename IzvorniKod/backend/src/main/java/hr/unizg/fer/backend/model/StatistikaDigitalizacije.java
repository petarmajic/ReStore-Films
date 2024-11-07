package hr.unizg.fer.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "STATISTIKA_DIGITALIZACIJE")
public class StatistikaDigitalizacije {

    @Id
    @Column(name = "IDStatistike", nullable = false)
    private int idStatistike;

    @Column(name = "BrojDigitaliziranih", nullable = false)
    private int brojDigitaliziranih;

    @Column(name = "BrojNaDigitalizaciji", nullable = false)
    private int brojNaDigitalizaciji;

    public StatistikaDigitalizacije() {
    }

    public StatistikaDigitalizacije(int idStatistike, int brojDigitaliziranih, int brojNaDigitalizaciji) {
        this.idStatistike = idStatistike;
        this.brojDigitaliziranih = brojDigitaliziranih;
        this.brojNaDigitalizaciji = brojNaDigitalizaciji;
    }

    public int getIdStatistike() {
        return idStatistike;
    }

    public void setIdStatistike(int idStatistike) {
        this.idStatistike = idStatistike;
    }

    public int getBrojDigitaliziranih() {
        return brojDigitaliziranih;
    }

    public void setBrojDigitaliziranih(int brojDigitaliziranih) {
        this.brojDigitaliziranih = brojDigitaliziranih;
    }

    public int getBrojNaDigitalizaciji() {
        return brojNaDigitalizaciji;
    }

    public void setBrojNaDigitalizaciji(int brojNaDigitalizaciji) {
        this.brojNaDigitalizaciji = brojNaDigitalizaciji;
    }
}

