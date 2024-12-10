package hr.unizg.fer.backend.service.secondary.impl;

import hr.unizg.fer.backend.model.secondary.FilmskaTrakaArhiva;
import hr.unizg.fer.backend.repository.secondary.FilmskaTrakaArhivaRepository;
import hr.unizg.fer.backend.service.secondary.FilmskaTrakaArhivaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FilmskaTrakaArhivaServiceImpl implements FilmskaTrakaArhivaService {

    private final FilmskaTrakaArhivaRepository filmskaTrakaArhivaRepository;

    @Autowired
    public FilmskaTrakaArhivaServiceImpl(FilmskaTrakaArhivaRepository filmskaTrakaArhivaRepository) {
        this.filmskaTrakaArhivaRepository = filmskaTrakaArhivaRepository;
    }



    public FilmskaTrakaArhiva getFilmskaTrakaArhivaByBarKod(String barKod) {
        Optional<FilmskaTrakaArhiva> filmskaTrakaArhiva =
                filmskaTrakaArhivaRepository.findFilmskaTrakaArhivaByBarKod(barKod);
        if (filmskaTrakaArhiva.isPresent()) {
            return filmskaTrakaArhiva.get();
        }else{
            throw new NoSuchElementException("U arhivi nema filma s ID = " + barKod + " !");
        }
    }
}
