package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.user.Role;
import be.kdg.processor.business.domain.user.User;
import be.kdg.processor.exceptions.UserNotFoundException;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
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

    public User save(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        return userRepository.save(user);
    }

    public User load(Long id) throws UserNotFoundException {

        Optional<User> userOptional = userRepository.findById(id);

        return userOptional.orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    public boolean delete(Long id) throws UserNotFoundException {

        Optional<User> account = userRepository.findById(id);
        if (account.isPresent()) {
            userRepository.delete(account.get());
            return true;
        } else throw new UserNotFoundException("User not found");
    }

    public Optional<User> loadByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (!optionalUser.isPresent()){
            throw new UsernameNotFoundException("Username not found.");
        } else {
            User user = optionalUser.get();
            return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
        }
    }

    @PostConstruct
    private void init() {
        HashSet hashSet = new HashSet();
        hashSet.add(new Role("ADMIN"));
        User demoUser = new User("joni.vr@hotmail.com", bCryptPasswordEncoder.encode("password"), "joni", "van roost", true, hashSet);

        if (!userRepository.findByEmail("joni.vr@hotmail.com").isPresent()) {
            userRepository.save(demoUser);
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }
}
