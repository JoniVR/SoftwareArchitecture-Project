package be.kdg.processor.persistence;

import be.kdg.processor.business.domain.violation.Violation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViolationRepository extends JpaRepository<Violation, Long>  {

    Optional<Violation> findFirstByLicensePlateOrderByCreationDateDesc(String licensePlate);
}
