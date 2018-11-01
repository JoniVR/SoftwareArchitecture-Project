package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.user.Role;
import be.kdg.processor.business.domain.user.User;
import be.kdg.processor.exceptions.UserException;
import be.kdg.processor.persistence.RoleRepository;
import be.kdg.processor.persistence.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User addUser(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(new Role("ADMIN")));
        return userRepository.save(user);
    }

    public User changeUser(User user) {

        return this.addUser(user);
    }

    public User loadUser(Long id) throws UserException {

        Optional<User> userOptional = userRepository.findById(id);

        return userOptional.orElseThrow(() -> new UserException("User not found."));
    }

    public List<User> loadAllUsers() {

        return userRepository.findAll();
    }

    public boolean deleteUser(Long id) throws UserException {

        Optional<User> account = userRepository.findById(id);
        if (account.isPresent()) {
            userRepository.delete(account.get());
            return true;
        } else throw new UserException("User not found");
    }

    public Optional<User> loadUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (!optionalUser.isPresent()){
            throw new UsernameNotFoundException("Username not found.");
        } else {
            User user = optionalUser.get();
            return new org.springframework.security.core.userdetails.User(user.getName() +" "+user.getLastName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
        }
    }

    // Set up some default users
    @PostConstruct
    public void init() {

        User demoUser = new User("joni.vr@hotmail.com", bCryptPasswordEncoder.encode("password"), "joni", "van roost", true, Set.of(new Role("ADMIN")));

        if (!userRepository.findByEmail("joni.vr@hotmail.com").isPresent()) {
            userRepository.save(demoUser);
        }

        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("USER"));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }
}
