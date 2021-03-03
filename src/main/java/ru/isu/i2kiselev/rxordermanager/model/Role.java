package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Role implements GrantedAuthority {

    private String role;

    @Override
    public String getAuthority() {
        return role;
    }

    public Role(String role) {
        this.role = role;
    }

}
