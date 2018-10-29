package be.kdg.processor.persistence;

import be.kdg.processor.business.domain.fine.Fine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FineRepository extends JpaRepository<Fine, Long> {

    /**
     * Find fines between two LocalDateTime values.
     * @param from starting LocalDateTime.
     * @param to ending LocalDateTime.
     * @return A list with fines filtered between two LocalDateTimes.
     */
    List<Fine> findByViolationCreationDateBetween(LocalDateTime from, LocalDateTime to);
}
