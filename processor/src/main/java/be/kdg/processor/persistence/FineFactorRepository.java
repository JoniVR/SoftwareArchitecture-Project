package be.kdg.processor.persistence;

import be.kdg.processor.business.domain.fine.FineFactor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FineFactorRepository extends JpaRepository<FineFactor, Long> {


}
