package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.violation.Violation;
import be.kdg.processor.exceptions.ViolationException;
import be.kdg.processor.persistence.ViolationRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ViolationService {

    private final ViolationRepository violationRepository;

    public ViolationService(ViolationRepository violationRepository) {
        this.violationRepository = violationRepository;
    }

    public Violation addViolation(Violation violation) {

        return violationRepository.save(violation);
    }

    public Optional<Violation> loadLatestViolationFrom(String licensePlate) {

        return violationRepository.findFirstByLicensePlateOrderByCreationDateDesc(licensePlate);
    }

    public Violation loadViolation(Long id) throws ViolationException {

        return violationRepository.findById(id).orElseThrow(() -> new ViolationException("Violation not found."));
    }
}
