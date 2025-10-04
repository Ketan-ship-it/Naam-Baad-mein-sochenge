package com.Fintech.User_Service.enitities;

import com.Fintech.User_Service.enitities.ENUMS.AuthProviders;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private AuthProviders provider;  // GOOGLE, LOCAL, etc.

    @Column(name = "provider_id")
    private String providerId;

    private String pictureUrl;

    private boolean emailVerified;
}
