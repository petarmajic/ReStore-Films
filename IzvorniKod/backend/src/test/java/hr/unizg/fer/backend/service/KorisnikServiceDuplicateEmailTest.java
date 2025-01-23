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

class KorisnikServiceDuplicateEmailTest {

    @Mock
    private KorisnikRepository korisnikRepository; // Mock repozitorija

    @InjectMocks
    private KorisnikServiceImpl korisnikService; // Testiramo servis

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicijalizacija mockova
    }

    @Test
    void testAddKorisnikWithDuplicateEmail() {
        // 1. Pripremamo podatke
        Korisnik existingKorisnik = new Korisnik(1L, "Ivan", "Ivić", "ii12345@fer.hr", null, null, null);
        Korisnik noviKorisnik = new Korisnik(null, "Ana", "Anić", "ii12345@fer.hr", null, null, null);

        // 2. Mockiramo ponašanje repozitorija
        when(korisnikRepository.findKorisnikByEmail("ii12345@fer.hr")).thenReturn(Optional.of(existingKorisnik));

        // 3. Izvršavamo metodu i očekujemo iznimku
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            korisnikService.addKorisnik(noviKorisnik);
        });

        // 4. Provjeravamo poruku iznimke
        assertEquals("Vec postoji korisnik s tim emailom!!!", exception.getMessage());

        // 5. Provjeravamo da metoda `save` nije pozvana
        verify(korisnikRepository, never()).save(any(Korisnik.class));
    }
}
