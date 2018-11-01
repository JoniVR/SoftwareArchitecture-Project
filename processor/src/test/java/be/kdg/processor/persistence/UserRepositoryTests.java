package be.kdg.processor.persistence;

import be.kdg.processor.business.domain.user.Role;
import be.kdg.processor.business.domain.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

/**
 * We will only test custom repository functions in here.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User user1;

    @Before
    public void setUp(){

        user1 = User.builder()
                .id(1L)
                .name("joni")
                .lastName("van roost")
                .email("test@test.com")
                .password("password")
                .active(true)
                .roles(Set.of(new Role("ADMIN")))
                .build();

        User user2 = User.builder()
                .id(2L)
                .name("steve")
                .lastName("jobs")
                .email("sjobs@apple.com")
                .password("password")
                .active(true)
                .roles(Set.of(new Role("USER")))
                .build();

        User user3 = User.builder()
                .id(3L)
                .name("John")
                .lastName("Marston")
                .email("jmarston@apple.com")
                .password("password")
                .active(true)
                .roles(Set.of(new Role("USER")))
                .build();


        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    @Test
    @Transactional
    public void testFindByEmail() {

        Optional<User> userOptional = userRepository.findByEmail(user1.getEmail());

        Assert.assertTrue(userOptional.isPresent());
        Assert.assertEquals(user1.getEmail(), userOptional.get().getEmail());
    }
}
