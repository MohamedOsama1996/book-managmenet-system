package com.banque.management.entity;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "role")
@Data
public class RoleEntity {


  @Id
  @Column(name = "role_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_generator")
  @SequenceGenerator(name = "role_generator", sequenceName = "role_sequence",initialValue = 1,allocationSize = 1)
  private int roleId;

  @Column(name ="role")
  private String role;


}
