package be.kdg.processor.controller;

import be.kdg.processor.exceptions.FineException;
import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.model.fine.FineDTO;
import be.kdg.processor.service.FineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FineController {

    @Autowired
    public FineService fineService;

    @Autowired
    public ModelMapper modelMapper;

    //TODO: correct error handling
    /**
     * Get a filtered list of fines between two LocalDateTimes.
     * @param fromDate The date from which we should start filtering. Format: yyyy-MM-dd'T'HH:mm:ss
     * @param toDate The date at which we should stop filtering. Format: yyyy-MM-dd'T'HH:mm:ss
     * @return A list of filtered fines.
     * @throws FineException In case no fines were found this will throw a FineException.
     */
    @GetMapping("/fines/")
    public List<FineDTO> getFinesBetween(@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime fromDate,
                                         @RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime toDate) throws FineException {

        List<Fine> fines = fineService.loadAllBetween(fromDate, toDate);

        return fines.stream().map(entity -> modelMapper.map(entity, FineDTO.class)).collect(Collectors.toList());
    }

    // goedkeuren van een boete
    @PutMapping("/fines/{id}")
    public ResponseEntity<FineDTO> updateFine(@PathVariable Long id, @RequestParam("isApproved") boolean isApproved) throws FineException {

        Fine fineIn = fineService.load(id);
        fineIn.setApproved(isApproved);
        Fine fineOut = fineService.save(fineIn);

        return new ResponseEntity<>(modelMapper.map(fineOut, FineDTO.class), HttpStatus.ACCEPTED);
    }
}
