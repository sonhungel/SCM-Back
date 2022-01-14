package com.scm.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class User extends SupperEntity implements UserDetails {
    @Column(unique = true)
    private String username;

    @Column(columnDefinition = "nvarchar(255)")
    private String fullName;

    @Column
    private String email;

    @Column
    @Size(min = 10, max = 11)
    private String phoneNumber;

    @Column
    private Date dateOfBirth;

    @Column(columnDefinition = "nvarchar(255)")
    private String address;

    @Column
    private String province;

    @Column
    private String ward;

    @Column
    private String district;

    @Column
    private String password;

    @Transient
    private String confirmPassword;

    @Column
    private Date create_At;

    @Column
    private Date update_At;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Invoice> invoiceSet;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<InventoryCheck> inventoryCheckSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "key.user", fetch = FetchType.LAZY)
    private List<UserRole> userRoleList;

    @PrePersist
    protected void onCreate(){
        this.create_At = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.update_At = new Date();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
