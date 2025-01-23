package hr.unizg.fer.backend.controller;

import hr.unizg.fer.backend.controller.primary.GrupaZaDigitalizacijuController;
import hr.unizg.fer.backend.model.primary.GrupaZaDigitalizaciju;
import hr.unizg.fer.backend.service.primary.GrupaZaDigitalizacijuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GrupaZaDigitalizacijuControllerTest {

    @Mock
    private GrupaZaDigitalizacijuService grupaZaDigitalizacijuService; // Mock servis

    @InjectMocks
    private GrupaZaDigitalizacijuController grupaZaDigitalizacijuController; // Kontroler koji testiramo

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicijalizacija mockova
    }

    @Test
    void testCreateGroup_NotFound() {
        // 1. Priprema ulaznih podataka
        GrupaZaDigitalizaciju invalidGroup = new GrupaZaDigitalizaciju();
        invalidGroup.setStatusDigitalizacije(null); // Simuliramo nevažeći status
        invalidGroup.setFilmskeTrake(List.of("Film 1", "Film 2")); // Dodajemo neke filmske trake

        // Simulacija iznimke u servisu
        when(grupaZaDigitalizacijuService.createGroup(invalidGroup))
                .thenThrow(new NoSuchElementException("Nevažeći podaci za grupu."));

        // 2. Poziv metode iz kontrolera
        ResponseEntity<GrupaZaDigitalizaciju> response = grupaZaDigitalizacijuController.createGroup(invalidGroup);

        // 3. Provjera odgovora
        assertEquals(404, response.getStatusCodeValue()); // Provjera da je vraćen 404 Not Found
        assertEquals(null, response.getBody()); // Provjera da tijelo odgovora nije postavljeno
    }
}