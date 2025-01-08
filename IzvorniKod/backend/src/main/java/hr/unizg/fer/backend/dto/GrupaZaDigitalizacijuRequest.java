package hr.unizg.fer.backend.dto;

import hr.unizg.fer.backend.model.primary.GrupaZaDigitalizaciju;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GrupaZaDigitalizacijuRequest {
    private List<String> nasloviFilmova;
    private GrupaZaDigitalizaciju grupaZaDigitalizaciju;
}
