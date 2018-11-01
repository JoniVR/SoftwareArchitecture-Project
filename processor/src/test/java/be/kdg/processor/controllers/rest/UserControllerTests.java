package be.kdg.processor.controllers.rest;

import be.kdg.processor.business.domain.user.Role;
import be.kdg.processor.business.domain.user.User;
import be.kdg.processor.business.service.UserService;
import be.kdg.processor.controller.rest.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Mock
    private UserService userServiceUnderTest;

    @InjectMocks
    private UserController userControllerUnderTest;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {

        initMocks(this);

        // Not using injection because that would trigger the @PostConstruct and mess with the data.
        userControllerUnderTest = new UserController(userServiceUnderTest, modelMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(userControllerUnderTest).build();
    }

    @Transactional
    @Test
    public void testGetAllUsers() throws Exception {

        User user1 = User.builder()
                .id(1L)
                .name("Joni")
                .lastName("Van Roost")
                .password("password")
                .email("joni.vr@hotmail.com")
                .roles(Set.of(new Role(1L, "ADMIN")))
                .active(true)
                .build();

        User user2 = User.builder()
                .id(2L)
                .name("Inoj")
                .lastName("Tsoor Nav")
                .password("password")
                .email("inoj.sn@hotmail.com")
                .roles(Set.of(new Role(2L, "ADMIN")))
                .active(true)
                .build();

        User user3 = User.builder()
                .id(3L)
                .name("Steve")
                .lastName("Jobs")
                .password("password")
                .email("sjobs@apple.com")
                .roles(Set.of(new Role(1L, "ADMIN")))
                .active(true)
                .build();

        Mockito.when(userServiceUnderTest.loadAllUsers())
                .thenReturn(List.of(user1, user2, user3));

        mockMvc.perform(get("/api/users/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Joni")))
                .andExpect(content().string(containsString("Inoj")))
                .andExpect(content().string(containsString("Steve")))
                .andDo(print());
    }

    @Transactional
    @Test
    public void testCreateUser() throws Exception {

        User userToCreate = new User(1L,"test@test.com", "password","joni","van roost", true, Set.of(new Role(1L,"ADMIN")));

        Mockito.when(userServiceUnderTest.addUser(userToCreate))
                .thenReturn(userToCreate);

        String requestJSON = objectMapper.writeValueAsString(userToCreate);

        mockMvc.perform(post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestJSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("test@test.com")))
                .andDo(print());
    }
}
