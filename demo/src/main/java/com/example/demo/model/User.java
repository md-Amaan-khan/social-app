package com.example.demo.model;

import com.example.demo.model.security.RoleEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String password;
    private String email;

    @ManyToOne
    @JoinColumn(name = "role")
    private RoleEntity role;
}
