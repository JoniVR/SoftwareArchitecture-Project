package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.user.User;
import be.kdg.processor.exceptions.UserNotFoundException;
import be.kdg.processor.persistence.RoleRepository;
import be.kdg.processor.persistence.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    private UserService userServiceUnderTest;
    private User user;

    @Before
    public void setUp() {

        initMocks(this);
        userServiceUnderTest = new UserService(mockUserRepository, mockRoleRepository, mockBCryptPasswordEncoder);
        user = User.builder()
                .id(1L)
                .name("Joni")
                .lastName("Van Roost")
                .email("test@test.com")
                .build();

        Mockito.when(mockUserRepository.save(any()))
                .thenReturn(user);

        Mockito.when(mockUserRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));

        Mockito.when(mockUserRepository.findById(any()))
                .thenReturn(Optional.of(user));
    }

    @Test
    @Transactional
    public void testSaveUser(){

        // Setup
        final String email = "test@test.com";

        // Run the test
        User result = userServiceUnderTest.save(User.builder().build());

        // Verify the results
        assertEquals(email, result.getEmail());
    }

    @Test
    @Transactional
    public void testLoadUser() throws UserNotFoundException {

        // Setup
        final Long id = user.getId();

        // Run the test
        final User result = userServiceUnderTest.load(id);

        // Verify the results
        assertNotNull(result);
        assertEquals(result.getId(), id);
    }

    @Test
    @Transactional
    public void testLoadUserByEmail() {

        // Setup
        final String email = "test@test.com";

        // Run the test
        final Optional<User> result = userServiceUnderTest.loadByEmail(email);

        // Verify the results
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }
}
