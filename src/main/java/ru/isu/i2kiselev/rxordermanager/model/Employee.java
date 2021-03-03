package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Employee POJO. It represent company's employee with array of tasks and respective completion estimates stored in RDBMS
 * @version 0.2
 * @author Ilya Kiselev
 */



@Data
@NoArgsConstructor
@Table("employee")
public class Employee implements UserDetails {

    @Id
    private Integer id;

    private String username;

    private String password;

    private String role;

    public Employee(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new Role(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
