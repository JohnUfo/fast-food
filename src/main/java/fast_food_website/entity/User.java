package fast_food_website.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import fast_food_website.entity.template.AbsEntity;

import java.util.Collection;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User extends AbsEntity implements UserDetails {

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String initialLetter;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment avatar;

    private String emailCode;

    @Enumerated(EnumType.STRING)
    @ManyToMany
    private Set<SystemRole> systemRole;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Basket basket;

    private boolean enabled = false;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean accountNonLocked = true;

    @PrePersist
    @PreUpdate
    public void setInitialLetterMyMethod() {
        this.initialLetter = fullName.substring(0, 1);
    }

    public User(String fullName, String password, String email, String emailCode, Set<SystemRole> systemRole) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.emailCode = emailCode;
        this.systemRole = systemRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.systemRole;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
