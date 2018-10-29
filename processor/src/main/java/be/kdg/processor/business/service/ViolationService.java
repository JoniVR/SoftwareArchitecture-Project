package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.violation.Violation;
import be.kdg.processor.exceptions.ViolationException;
import be.kdg.processor.persistence.ViolationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ViolationService {

    @Autowired
    private ViolationRepository violationRepository;

    public Violation save(Violation violation) {

        return violationRepository.save(violation);
    }

    public Optional<Violation> loadLatestViolationFrom(String licensePlate) {

        return violationRepository.findFirstByLicensePlateOrderByCreationDateDesc(licensePlate);
    }

    public Violation load(Long id) throws ViolationException {

        Optional<Violation> optionalViolation = violationRepository.findById(id);

        if (optionalViolation.isPresent()){
            return optionalViolation.get();
        } else {
            throw new ViolationException("Violation not Found.");
        }
    }
}
