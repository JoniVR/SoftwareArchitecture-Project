package be.kdg.processor.service;

import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.persistence.FineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class FineService {

    @Autowired
    private FineRepository fineRepository;

    public Fine save(Fine fine) {

        return fineRepository.save(fine);
    }
}
