package shopper.Models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @NotNull
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    @Size(max = 100, message = "Please, enter email, lower than 100 letters")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Please, provide a valid username")
    @NotNull
    @Size(min = 1, max = 50, message = "Size of username must be between 1 and 50")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Please, provide a valid password")
    @NotNull
    @Size(min = 1, max = 255, message = "Size of password must be between 1 and 255")
    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private int active;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
