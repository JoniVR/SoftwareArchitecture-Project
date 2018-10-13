package be.kdg.processor.persistence;

import be.kdg.processor.model.fine.Fine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FineRepository extends JpaRepository<Fine, Long> {


}
