package hr.unizg.fer.backend.model.primary;

import jakarta.persistence.*;

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

    public StatistikaDigitalizacije() {
    }

    public StatistikaDigitalizacije(Long idStatistike, Integer brojDigitaliziranih, Integer brojNaDigitalizaciji) {
        this.idStatistike = idStatistike;
        this.brojDigitaliziranih = brojDigitaliziranih;
        this.brojNaDigitalizaciji = brojNaDigitalizaciji;
    }

    public Long getIdStatistike() {
        return idStatistike;
    }

    public void setIdStatistike(Long idStatistike) {
        this.idStatistike = idStatistike;
    }

    public Integer getBrojDigitaliziranih() {
        return brojDigitaliziranih;
    }

    public void setBrojDigitaliziranih(Integer brojDigitaliziranih) {
        this.brojDigitaliziranih = brojDigitaliziranih;
    }

    public Integer getBrojNaDigitalizaciji() {
        return brojNaDigitalizaciji;
    }

    public void setBrojNaDigitalizaciji(Integer brojNaDigitalizaciji) {
        this.brojNaDigitalizaciji = brojNaDigitalizaciji;
    }
}

