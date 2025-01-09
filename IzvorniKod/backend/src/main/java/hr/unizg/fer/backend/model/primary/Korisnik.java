package hr.unizg.fer.backend.model.primary;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Uloga", nullable = true)
    private UlogaKorisnika uloga;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Korisnik_GrupaZaDigitalizaciju_iznio",
                    joinColumns = @JoinColumn(name = "iznioIzSkladistaKorisnikId"))
    @Column(name = "idIznesenihGrupaZaDigitalizaciju")
    private List<Long> iznioIzSkladistaGrupeZaDigitalizaciju = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Korisnik_GrupaZaDigitalizaciju_vratio",
            joinColumns = @JoinColumn(name = "vratioUSkladisteKorisnikId"))
    @Column(name = "idVracenihGrupaZaDigitalizaciju")
    private List<Long> vratioUSkladisteGrupeZaDigitalizaciju = new ArrayList<>();

}

