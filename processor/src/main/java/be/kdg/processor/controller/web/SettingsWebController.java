package be.kdg.processor.controller.web;

import be.kdg.processor.business.domain.settings.Settings;
import be.kdg.processor.business.domain.settings.SettingsDTO;
import be.kdg.processor.business.service.SettingsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/settings")
public class SettingsWebController {

    private final SettingsService settingsService;
    private final ModelMapper modelMapper;

    public SettingsWebController(SettingsService settingsService, ModelMapper modelMapper) {
        this.settingsService = settingsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ModelAndView showSettings() {

        Settings settings = settingsService.loadSettings();
        SettingsDTO settingsDTO = modelMapper.map(settings, SettingsDTO.class);

        return new ModelAndView("settings", "settingsDTO", settingsDTO);
    }

    @PostMapping
    public ModelAndView saveSettings(SettingsDTO settingsDTO) {

        Settings settingsIn = modelMapper.map(settingsDTO, Settings.class);
        Settings settingsOut = settingsService.updateSettings(settingsIn);
        SettingsDTO settingsOutDTO = modelMapper.map(settingsOut, SettingsDTO.class);

        return new ModelAndView("settings", "settingsDTO", settingsOutDTO);
    }
}