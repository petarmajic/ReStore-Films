package hr.unizg.fer.backend.service;

import hr.unizg.fer.backend.model.primary.GrupaZaDigitalizaciju;
import hr.unizg.fer.backend.repository.primary.FilmskaTrakaRepository;
import hr.unizg.fer.backend.repository.primary.GrupaZaDigitalizacijuRepository;
import hr.unizg.fer.backend.service.primary.impl.GrupaZaDigitalizacijuServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.when;

class GrupaZaDigitalizacijuServiceTest {

    @Mock
    private GrupaZaDigitalizacijuRepository grupaZaDigitalizacijuRepository;

    @Mock
    private FilmskaTrakaRepository filmskaTrakaRepository;

    @InjectMocks
    private GrupaZaDigitalizacijuServiceImpl grupaZaDigitalizacijuService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicijalizacija mockova
    }

    @Test
    void testCreateGroup_ShouldThrowExceptionForNonExistentFilm() {
        // 1. Priprema ulaznih podataka
        GrupaZaDigitalizaciju invalidGroup = new GrupaZaDigitalizaciju();
        invalidGroup.setFilmskeTrake(List.of("Nepostojeći Film")); // Film koji ne postoji

        // 2. Mockiranje ponašanja repozitorija
        when(filmskaTrakaRepository.findFilmskaTrakaByNaslov("Nepostojeći Film"))
                .thenReturn(java.util.Optional.empty()); // Simulacija da film ne postoji

        // 3. Izvršavanje metode koja bi trebala baciti iznimku
        grupaZaDigitalizacijuService.createGroup(invalidGroup); // Ovo će baciti iznimku
    }
}
