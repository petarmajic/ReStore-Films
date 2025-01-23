package hr.unizg.fer.backend.service;

import hr.unizg.fer.backend.model.primary.Korisnik;
import hr.unizg.fer.backend.repository.primary.KorisnikRepository;
import hr.unizg.fer.backend.service.primary.impl.KorisnikServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KorisnikServiceTest {

    @Mock
    private KorisnikRepository korisnikRepository; // Mock repozitorija

    @InjectMocks
    private KorisnikServiceImpl korisnikService; // Servis koji testiramo

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicijalizacija mockova
    }

    @Test
    void testAddKorisnik() {
        // 1. Definiramo ulazne podatke
        Korisnik noviKorisnik = new Korisnik(null, "Ivan", "Ivic", "ii12345@fer.hr", null, null, null);

        // 2. Definiramo ponašanje mocka
        when(korisnikRepository.findKorisnikByEmail(noviKorisnik.getEmail())).thenReturn(Optional.empty());
        when(korisnikRepository.save(any(Korisnik.class))).thenReturn(noviKorisnik);

        // 3. Pozivamo metodu servisa
        Korisnik savedKorisnik = korisnikService.addKorisnik(noviKorisnik);

        // 4. Provjeravamo rezultate
        assertNotNull(savedKorisnik);
        assertEquals("ii12345@fer.hr", savedKorisnik.getEmail());
        assertEquals("Ivan", savedKorisnik.getIme());
        assertEquals("Ivic", savedKorisnik.getPrezime());

        // 5. Provjeravamo interakcije s mockom
        verify(korisnikRepository, times(1)).findKorisnikByEmail(noviKorisnik.getEmail());
        verify(korisnikRepository, times(1)).save(any(Korisnik.class));
    }
}