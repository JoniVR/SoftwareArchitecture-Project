package be.kdg.processor.service;

import be.kdg.processor.domain.fine.FineFactor;
import be.kdg.processor.persistence.FineFactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class FineFactorService {

    @Autowired
    private FineFactorRepository fineFactorRepository;

    public FineFactor loadFineFactor() {

        Optional<FineFactor> fineFactorOptional = fineFactorRepository.findById(1L);
        return fineFactorOptional.orElseGet(this::createFineFactor);
    }

    public FineFactor updateFineFactor(FineFactor fineFactor) {

        return fineFactorRepository.save(fineFactor);
    }

    private FineFactor createFineFactor() {

        FineFactor fineFactor = new FineFactor();

        return fineFactorRepository.save(fineFactor);
    }
}
