package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.fine.Fine;
import be.kdg.processor.exceptions.FineException;
import be.kdg.processor.persistence.FineRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FineService {

    private final FineRepository fineRepository;

    public FineService(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    public Fine save(Fine fine) {

        return fineRepository.save(fine);
    }

    public Fine updateFineApproved(Long id, boolean isApproved) throws FineException {

        Optional<Fine> optionalFine = fineRepository.findById(id);
        if (optionalFine.isPresent()) {
            Fine fine = optionalFine.get();
            fine.setApproved(isApproved);
            return fineRepository.save(fine);
        } else {
            throw new FineException("Fine not found.");
        }
    }

    public Fine updateFineAmount(Long id, double amount, String comment) throws FineException {

        Optional<Fine> optionalFine = fineRepository.findById(id);
        if (optionalFine.isPresent()) {
            Fine fine = optionalFine.get();
            fine.setAmount(amount);
            fine.setComments(comment);
            return fineRepository.save(fine);
        } else {
            throw new FineException("Fine not found.");
        }
    }

    /**
     * Get a filtered list of fines between two LocalDateTimes.
     *
     * @param from The date from which we should start filtering. Format: yyyy-MM-dd'T'HH:mm:ss
     * @param to   The date at which we should stop filtering. Format: yyyy-MM-dd'T'HH:mm:ss
     * @return A list of filtered fines.
     */
    public List<Fine> loadAllFinesBetween(LocalDateTime from, LocalDateTime to) {

        return fineRepository.findByViolationCreationDateBetween(from, to);
    }
}
