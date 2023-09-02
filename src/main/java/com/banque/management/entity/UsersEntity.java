package com.banque.management.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UsersEntity implements UserDetails {


  @Id
  @Column(name = "users_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
  @SequenceGenerator(name = "users_generator", sequenceName = "users_sequence",initialValue = 1,allocationSize = 1)
  private int usersId;

  @Column(name ="user_name")
  private String userName;

  @Column(name = "user_password")
  private String userPassword;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "role_id",referencedColumnName = "role_id")
  private RoleEntity roleEntity;

  public UsersEntity(int usersId, String userName, String userPassword, RoleEntity roleEntity) {
    this.usersId = usersId;
    this.userName = userName;
    this.userPassword = userPassword;
    this.roleEntity = roleEntity;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    String role = this.getRoleEntity().getRole();
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    authorities.add(new SimpleGrantedAuthority(role));

    return authorities;
  }

  @Override
  public String getPassword() {
    return userPassword;
  }

  @Override
  public String getUsername() {
    return userName;
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
