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

    @Size(min = 1, message = "Username must be at least 4 letters")
    @Size(max = 50, message = "Username must be lower than 50 letters")
    @Column(name = "username")
    private String username;

    @Size(min = 1, message = "Password must be at least 4 letters")
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
