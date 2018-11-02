package be.kdg.processor.controller.web;

import be.kdg.processor.business.domain.settings.Settings;
import be.kdg.processor.business.domain.settings.settingsDTO;
import be.kdg.processor.business.service.SettingsService;
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
    private SettingsService settingsService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/finefactor")
    public ModelAndView showFineFactorForm() {

        Settings settings = settingsService.loadSettings();
        settingsDTO settingsDTO = modelMapper.map(settings, settingsDTO.class);

        return new ModelAndView("finefactorform", "fineFactorDTO", settingsDTO);
    }

    @PostMapping("/finefactor")
    public ModelAndView saveFineFactor(settingsDTO settingsDTO) {

        Settings settings = modelMapper.map(settingsDTO, Settings.class);
        settings.setId(1L);
        settingsService.updateSettings(settings);

        return new ModelAndView("finefactorform", "fineFactorDTO", settingsDTO);
    }
}