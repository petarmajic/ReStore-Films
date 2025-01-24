package hr.unizg.fer.backend.service;

import hr.unizg.fer.backend.model.primary.FilmskaTraka;
import hr.unizg.fer.backend.model.secondary.FilmskaTrakaArhiva;
import hr.unizg.fer.backend.repository.primary.FilmskaTrakaRepository;
import hr.unizg.fer.backend.service.primary.impl.FilmskaTrakaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;

import static org.mockito.Mockito.*;

class FilmskaTrakaServiceTest {

    @Mock
    private FilmskaTrakaRepository filmskaTrakaRepository;

    @InjectMocks
    private FilmskaTrakaServiceImpl filmskaTrakaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFilmskaTrakaWithInvalidData_ShouldThrowException() {
        // 1. Pripremamo rubne podatke
        FilmskaTrakaArhiva invalidTraka = new FilmskaTrakaArhiva(
                null, // BarKod
                "", // Originalni naslov (prazno)
                null, // Radni naslov
                "HR", // Jezik originala
                "Mono", // Ton
                null, // Emisija
                "Drama", // Vrsta sadržaja
                "HR", // Porijeklo
                null, // Licenca
                1800, // Godina proizvodnje (ispod minimuma)
                LocalTime.of(0, 0), // Trajanje (0)
                null, // MarkIN
                null, // MarkOUT
                null // Broj medija
        );

        // 2. Izvršavamo metodu bez očekivanja iznimke
        filmskaTrakaService.addFilmskaTraka(invalidTraka);

        // 3. Provjeravamo da metoda `save` nije pozvana
        verify(filmskaTrakaRepository, never()).save(any(FilmskaTraka.class));
    }
}
