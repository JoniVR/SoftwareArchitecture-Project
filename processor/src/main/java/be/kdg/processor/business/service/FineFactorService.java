package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.fine.FineFactor;
import be.kdg.processor.persistence.FineFactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class FineFactorService {

    @Autowired
    private FineFactorRepository fineFactorRepository;

    public FineFactor loadFineFactor() {

        return fineFactorRepository.findById(1L).orElseGet(this::createFineFactor);
    }

    public FineFactor updateFineFactor(FineFactor fineFactor) {

        return fineFactorRepository.save(fineFactor);
    }

    private FineFactor createFineFactor() {

        FineFactor fineFactor = new FineFactor();

        return fineFactorRepository.save(fineFactor);
    }
}
