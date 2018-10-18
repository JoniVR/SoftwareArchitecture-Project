package be.kdg.processor.controller.web;

import be.kdg.processor.model.fine.FineFactor;
import be.kdg.processor.model.fine.FineFactorDTO;
import be.kdg.processor.service.FineFactorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/settings")
public class FineFactorWebController {

    @Autowired
    private FineFactorService fineFactorService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/finefactor")
    public ModelAndView showFineFactorForm() {

        FineFactor fineFactor = fineFactorService.loadFineFactor();
        FineFactorDTO fineFactorDTO = modelMapper.map(fineFactor, FineFactorDTO.class);

        return new ModelAndView("finefactorform", "fineFactorDTO", fineFactorDTO);
    }

    @PostMapping("/finefactor")
    public ModelAndView saveFineFactor(FineFactorDTO fineFactorDTO) {

        FineFactor fineFactor = modelMapper.map(fineFactorDTO, FineFactor.class);
        fineFactor.setId(1L);
        fineFactorService.updateFineFactor(fineFactor);

        return new ModelAndView("finefactorform", "fineFactorDTO", fineFactorDTO);
    }
}