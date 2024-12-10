package hr.unizg.fer.backend.service.primary.impl;

import hr.unizg.fer.backend.model.primary.StatusDigitalizacije;
import hr.unizg.fer.backend.repository.primary.GrupaZaDigitalizacijuRepository;
import hr.unizg.fer.backend.service.primary.GrupaZaDigitalizacijuService;
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

    public Map<StatusDigitalizacije, Integer> getFilmCountByStatus() {
        List<Object[]> results = repository.countFilmsByStatus();
        Map<StatusDigitalizacije, Integer> statusCounts = new HashMap<>();
        for (Object[] result : results) {
            statusCounts.put((StatusDigitalizacije) result[0], (Integer) result[1]);
        }
        return statusCounts;
    }
    @Override
    public List<Object[]> countGroupsTakenOutByUser() {
        return repository.countGroupsTakenOutByUser();
    }

    @Override
    public List<Object[]> countGroupsReturnedByUser() {
        return repository.countGroupsReturnedByUser();
    }
}
