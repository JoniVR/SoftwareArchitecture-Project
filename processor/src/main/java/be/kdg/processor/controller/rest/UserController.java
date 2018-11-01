package be.kdg.processor.controller.rest;

import be.kdg.processor.business.domain.user.User;
import be.kdg.processor.business.domain.user.UserDTO;
import be.kdg.processor.business.service.UserService;
import be.kdg.processor.exceptions.UserException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/users/")
    public List<UserDTO> getAllUsers() {

        List<User> users = userService.loadAllUsers();

        return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @PostMapping(value = "/users/")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {

        User userIn = modelMapper.map(userDTO, User.class);
        User userOut = userService.addUser(userIn);

        return new ResponseEntity<>(modelMapper.map(userOut, UserDTO.class), HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) throws UserException {

        User userIn = modelMapper.map(userDTO, User.class);
        User userOut = userService.changeUser(userIn);

        return new ResponseEntity<>(modelMapper.map(userOut, UserDTO.class), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/users/{id}")
    public void  deleteUser(@PathVariable Long id) throws UserException {

        userService.deleteUser(id);
    }
}