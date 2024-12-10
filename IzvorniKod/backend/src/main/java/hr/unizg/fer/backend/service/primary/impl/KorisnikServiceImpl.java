package hr.unizg.fer.backend.service.primary.impl;

import hr.unizg.fer.backend.model.primary.Korisnik;
import hr.unizg.fer.backend.repository.primary.KorisnikRepository;
import hr.unizg.fer.backend.service.primary.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KorisnikServiceImpl implements KorisnikService {
    private final KorisnikRepository korisnikRepository;

    @Autowired
    public KorisnikServiceImpl(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    @Override
    public Korisnik getKorisnikByEmail(String email) {
        Optional<Korisnik> korisnik = korisnikRepository.findKorisnikByEmail(email);
        return korisnik.orElse(null);
    }

    // by default je djelatnik
    @Override
    public Korisnik saveKorisnik(Korisnik korisnik) {
        System.out.println(korisnik);
        if(korisnik.getEmail() == null){
            throw new IllegalArgumentException("Email ne smije biti null!!!");
        }
        else if(korisnikRepository.findKorisnikByEmail(korisnik.getEmail()).isPresent()){
            throw new IllegalArgumentException("Vec postoji korisnik s tim emailom!!!");
        }
        return korisnikRepository.save(korisnik);
    }
}
