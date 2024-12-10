package hr.unizg.fer.backend.service.impl;

import hr.unizg.fer.backend.model.primary.StatusDigitalizacije;
import hr.unizg.fer.backend.repository.primary.GrupaZaDigitalizacijuRepository;
import hr.unizg.fer.backend.service.GrupaZaDigitalizacijuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GrupaZaDigitalizacijuServiceImpl implements GrupaZaDigitalizacijuService {

    private final GrupaZaDigitalizacijuRepository repository;

    @Autowired
    public GrupaZaDigitalizacijuServiceImpl(GrupaZaDigitalizacijuRepository repository) {
        this.repository = repository;
    }

    public Map<StatusDigitalizacije, Long> getFilmCountByStatus() {
        List<Object[]> results = repository.countFilmsByStatus();
        Map<StatusDigitalizacije, Long> statusCounts = new HashMap<>();
        for (Object[] result : results) {
            statusCounts.put((StatusDigitalizacije) result[0], (Long) result[1]);
        }
        return statusCounts;
    }
}
