package com.example.demo.security;

import com.example.demo.entity.UserApp;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final UserApp userApp;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(UserApp userApp) {
        this.userApp = userApp;
        this.authorities = new ArrayList<>();

        if (userApp.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userApp.getRole().getName()));

            if (userApp.getRole().getDefaultPermissions() != null) {
                userApp.getRole().getDefaultPermissions().forEach(permission ->
                        authorities.add(new SimpleGrantedAuthority(permission.getCode()))
                );
            }
        }

        if (userApp.getUserPermissions() != null) {
            userApp.getUserPermissions().stream()
                    .filter(up -> up.getIsGranted() != null && up.getIsGranted())
                    .forEach(up ->
                            authorities.add(new SimpleGrantedAuthority(up.getPermission().getCode()))
                    );
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userApp.getPassword();
    }

    @Override
    public String getUsername() {
        return userApp.getEmail();
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
