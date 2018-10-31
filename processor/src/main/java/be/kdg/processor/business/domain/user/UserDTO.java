package be.kdg.processor.business.domain.user;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {

    private String email;
    private String name;
    private String lasteName;
    private String password;
    private boolean isActive;
    private Set<Role> roles;
}
