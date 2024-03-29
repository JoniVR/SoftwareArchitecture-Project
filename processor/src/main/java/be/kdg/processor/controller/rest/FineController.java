package be.kdg.processor.controller.rest;

import be.kdg.processor.business.domain.fine.Fine;
import be.kdg.processor.business.domain.fine.FineDTO;
import be.kdg.processor.business.service.FineService;
import be.kdg.processor.exceptions.FineException;
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

    /**
     * Get a filtered list of fines between two LocalDateTimes.
     * @param fromDate The date from which we should start filtering. Format: yyyy-MM-dd'T'HH:mm:ss
     * @param toDate The date at which we should stop filtering. Format: yyyy-MM-dd'T'HH:mm:ss
     * @return A list of filtered fines.
     */
    @GetMapping("/fines/")
    public List<FineDTO> getFinesBetween(@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime fromDate,
                                         @RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime toDate) {

        List<Fine> fines = fineService.loadAllFinesBetween(fromDate, toDate);

        return fines.stream().map(entity -> modelMapper.map(entity, FineDTO.class)).collect(Collectors.toList());
    }

    /**
     * This method allows us to update a fine "isApproved" status.
     * @param id The id of the Fine object as a Long.
     * @param isApproved The boolean value that you want to set.
     * @return A FineDTO object.
     * @throws FineException In case no fines were found this will throw a FineException.
     */
    @PutMapping(value = "/fines/{id}", params = "isApproved")
    public ResponseEntity<FineDTO> updateFineApproved(@PathVariable Long id,
                                              @RequestParam("isApproved") boolean isApproved) throws FineException {

        Fine fineOut = fineService.updateFineApproved(id, isApproved);

        return new ResponseEntity<>(modelMapper.map(fineOut, FineDTO.class), HttpStatus.ACCEPTED);
    }

    /**
     * This method allows us to update the fine "amount" and add a comment.
     * @param id The id of the Fine object as a Long.
     * @param amount The amount of the fine.
     * @param comment A comment as to why the fine amount was changed.
     * @return A FineDTO object.
     * @throws FineException In case no fines were found this will throw a FineException.
     */
    @PutMapping(value = "/fines/{id}", params = {"amount","comments"})
    public ResponseEntity<FineDTO> updateFineAmount(@PathVariable Long id,
                                              @RequestParam("amount") double amount,
                                              @RequestParam("comments") String comment) throws FineException {

        Fine fineOut = fineService.updateFineAmount(id, amount, comment);

        return new ResponseEntity<>(modelMapper.map(fineOut, FineDTO.class), HttpStatus.ACCEPTED);
    }
}
