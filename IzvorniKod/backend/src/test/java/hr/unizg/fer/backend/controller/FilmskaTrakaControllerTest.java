package hr.unizg.fer.backend.controller;

import hr.unizg.fer.backend.model.primary.FilmskaTraka;
import hr.unizg.fer.backend.controller.primary.FilmskaTrakaController;
import hr.unizg.fer.backend.service.primary.FilmskaTrakaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilmskaTrakaControllerTest {

    @Mock
    private FilmskaTrakaService filmskaTrakaService; // Mock za servis

    @InjectMocks
    private FilmskaTrakaController filmskaTrakaController; // Kontroler koji testiramo

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicijalizacija mockova
    }

    @Test
    void testGetFilmskaTrakaById_Success() {
        // 1. Pripremamo podatke
        Long validId = 1L;
        FilmskaTraka expectedTraka = new FilmskaTraka();
        expectedTraka.setIdEmisije(validId);
        expectedTraka.setOriginalniNaslov("Test Film");

        when(filmskaTrakaService.getFilmskaTrakaById(validId)).thenReturn(expectedTraka);

        // 2. Izvršavamo metodu kontrolera
        ResponseEntity<FilmskaTraka> response = filmskaTrakaController.getFilmskaTrakaById(validId);

        // 3. Provjeravamo rezultat
        assertNotNull(response, "Response ne bi smio biti null.");
        assertEquals(200, response.getStatusCodeValue(), "HTTP status bi trebao biti 200 OK.");
        assertNotNull(response.getBody(), "Tijelo odgovora (body) ne bi smjelo biti null.");
        assertEquals(expectedTraka, response.getBody(), "Vraćena filmska traka ne odgovara očekivanoj.");

        // 4. Provjeravamo interakciju sa servisom
        verify(filmskaTrakaService, times(1)).getFilmskaTrakaById(validId);
    }
}
