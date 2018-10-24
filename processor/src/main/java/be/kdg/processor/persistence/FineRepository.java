package be.kdg.processor.persistence;

import be.kdg.processor.domain.fine.Fine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FineRepository extends JpaRepository<Fine, Long> {

    /**
     * Find fines between two LocalDateTime values.
     * @param from starting LocalDateTime.
     * @param to ending LocalDateTime.
     * @return A list with fines filtered between two LocalDateTimes.
     */
    List<Fine> findByCreationDateBetween(LocalDateTime from, LocalDateTime to);

    Optional<Fine> findFirstByLicensePlateOrderByCreationDateDesc(String licensePlate);
}
