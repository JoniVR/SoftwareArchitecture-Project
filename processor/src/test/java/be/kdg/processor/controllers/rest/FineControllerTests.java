package be.kdg.processor.controllers.rest;


import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.model.fine.FineDTO;
import be.kdg.processor.model.fine.FineType;
import be.kdg.processor.service.FineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FineControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FineService fineService;

    @Test
    @Transactional
    public void testUpdateFineApproved() throws Exception {

        // setup data to change
        Fine fine = new Fine(1L,10, FineType.EMISSION, false, null, "1-ABC-123", LocalDateTime.now());
        fineService.save(fine);

        FineDTO fineDTO = modelMapper.map(fine, FineDTO.class);
        String requestJSON = objectMapper.writeValueAsString(fineDTO);

        mockMvc.perform(put("/api/fines/{id}", 1L).param("isApproved", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJSON))
                .andExpect(status().isAccepted())
                .andExpect(content().string(containsString("true")));
    }
}
