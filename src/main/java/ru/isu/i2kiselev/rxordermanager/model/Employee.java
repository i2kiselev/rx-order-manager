package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;

/**
 * Employee POJO. It represent company's employee and its auth credentials
 * @version 0.7
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

    public Employee(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Employee withEncodedPassword(PasswordEncoder encoder){
        return new Employee(username,encoder.encode(password),"ROLE_EMPLOYEE");
        //return new Employee(username,encoder.encode(password),"ROLE_ADMIN");
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
