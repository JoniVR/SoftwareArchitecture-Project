package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.user.Role;
import be.kdg.processor.business.domain.user.User;
import be.kdg.processor.exceptions.UserException;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    private User deleteUser;

    @Before
    public void setUp() {

        initMocks(this);

        userServiceUnderTest = new UserService(mockUserRepository, mockRoleRepository, mockBCryptPasswordEncoder);
        user = User.builder()
                .id(1L)
                .name("Joni")
                .lastName("Van Roost")
                .email("test@test.com")
                .roles(Set.of(new Role(1L,"USER")))
                .build();

        Mockito.when(mockUserRepository.save(any()))
                .thenReturn(user);

        Mockito.when(mockUserRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));

        Mockito.when(mockUserRepository.findById(any()))
                .thenReturn(Optional.of(user));

        Mockito.when(mockUserRepository.findAll())
                .thenReturn(List.of(user));

        deleteUser = User.builder()
                .id(10L)
                .name("test")
                .lastName("testlast")
                .email("testlast@test.com")
                .password("password")
                .active(true)
                .build();
        mockUserRepository.save(deleteUser);
    }

    @Test
    @Transactional
    public void testAddUser(){

        // Setup
        final String email = "test@test.com";

        // Run the test
        User result = userServiceUnderTest.addUser(User.builder().build());

        // Verify the results
        assertEquals(email, result.getEmail());
    }

    @Test
    @Transactional
    public void testLoadUser() throws UserException {

        // Setup
        final Long id = user.getId();

        // Run the test
        final User result = userServiceUnderTest.loadUser(id);

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
        final Optional<User> result = userServiceUnderTest.loadUserByEmail(email);

        // Verify the results
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }

    @Test
    @Transactional
    public void changeUser() {

        // Setup
        final String email = "test@test.com";

        // Run the test
        User result = userServiceUnderTest.changeUser(User.builder().build());

        // verify the results
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    @Transactional
    public void loadAllUsers() {

        // Run the test
        List<User> result = userServiceUnderTest.loadAllUsers();

        assertEquals(1, result.size());
    }

    @Test
    @Transactional
    public void testDeleteUser() throws UserException {

        boolean isSuccess = userServiceUnderTest.deleteUser(deleteUser.getId());

        assertTrue(isSuccess);
    }
}
