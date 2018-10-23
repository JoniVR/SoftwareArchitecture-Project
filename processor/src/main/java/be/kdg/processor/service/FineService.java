package be.kdg.processor.service;

import be.kdg.processor.exceptions.FineException;
import be.kdg.processor.domain.fine.Fine;
import be.kdg.processor.persistence.FineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FineService {

    @Autowired
    private FineRepository fineRepository;

    public Fine save(Fine fine) {

        return fineRepository.save(fine);
    }

    public Fine load(Long id) throws FineException {

        Optional<Fine> optionalFine = fineRepository.findById(id);
        if (optionalFine.isPresent()){
            return optionalFine.get();
        }
        throw new FineException("Fine not found");
    }

    public List<Fine> loadAll() {

        return fineRepository.findAll();
    }

    /**
     * Get a filtered list of fines between two LocalDateTimes.
     * @param from The date from which we should start filtering. Format: yyyy-MM-dd'T'HH:mm:ss
     * @param to The date at which we should stop filtering. Format: yyyy-MM-dd'T'HH:mm:ss
     * @return A list of filtered fines.
     * @throws FineException In case no fines were found this will throw a FineException.
     */
    public List<Fine> loadAllBetween(LocalDateTime from, LocalDateTime to) throws FineException {

        return fineRepository.findByCreationDateBetween(from, to);
    }
}
