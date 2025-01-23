package hr.unizg.fer.backend.controller;

import hr.unizg.fer.backend.controller.primary.KorisnikController;
import hr.unizg.fer.backend.model.primary.Korisnik;
import hr.unizg.fer.backend.service.primary.KorisnikService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class KorisnikControllerTest {

    @Mock
    private KorisnikService korisnikService; // Mock servis

    @InjectMocks
    private KorisnikController korisnikController; // Kontroler koji testiramo

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicijalizacija mockova
    }

    @Test
    void testGetKorisnikByEmail_NotFound() {
        // 1. Simuliramo poziv za e-mail koji ne postoji
        String nonExistentEmail = "no12345@fer.hr";
        when(korisnikService.getKorisnikByEmail(nonExistentEmail)).thenReturn(null);

        // 2. Poziv metode iz kontrolera
        ResponseEntity<Korisnik> response = korisnikController.getKorisnikByEmail(nonExistentEmail);

        // 3. Provjera odgovora
        assertEquals(404, response.getStatusCodeValue()); // Provjera da je vraćen 404 Not Found
        assertEquals(null, response.getBody()); // Provjera da tijelo odgovora nije postavljeno
    }
}