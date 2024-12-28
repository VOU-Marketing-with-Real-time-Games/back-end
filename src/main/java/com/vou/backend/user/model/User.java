package com.vou.backend.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String avatar;
    private String fullName;
    private String email;
    private String password;
    private String role;
    private String status;
    private String phoneNumber;
    private String username;
    private Date dob;
    private String gender;
    private String facebookLink;
    private Integer turnNum;
    private Date createdAt;

    public void copy(User user) {
        // Copy properties that are not null
        if (user.getAvatar() != null) {
            this.setAvatar(user.getAvatar());
        }
        if (user.getFullName() != null) {
            this.setFullName(user.getFullName());
        }
        if (user.getEmail() != null) {
            this.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            this.setPassword(user.getPassword());
        }
        if (user.getRole() != null) {
            this.setRole(user.getRole());
        }
        if (user.getStatus() != null) {
            this.setStatus(user.getStatus());
        }
        if (user.getPhoneNumber() != null) {
            this.setPhoneNumber(user.getPhoneNumber());
        }
        if (user.getUsername() != null) {
            this.setUsername(user.getUsername());
        }
        if (user.getDob() != null) {
            this.setDob(user.getDob());
        }
        if (user.getGender() != null) {
            this.setGender(user.getGender());
        }
        if (user.getFacebookLink() != null) {
            this.setFacebookLink(user.getFacebookLink());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+getRole().toUpperCase()));
        return authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }
    @Override
    public String getUsername() {
        return this.username;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
