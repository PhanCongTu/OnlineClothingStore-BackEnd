package com.example.tuonlineclothingstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique=true)
    private String userName;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    private String avatar;

    @Column(length = 10)
    private String phoneNumber;

    @Email
    private String email;

    @Column
    private Boolean isActive = true;

    @Column
    private Date createAt= new Date(new java.util.Date().getTime());

    @Column
    private Date updateAt= new Date(new java.util.Date().getTime());

    @ElementCollection(fetch = FetchType.EAGER)
    @Column
    private List<String> roles = new ArrayList<String>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private List<Cart> carts;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private List<Order> orders;

    public User(String name, String userName, String password, String phoneNumber, String email, List<String> roles) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roles = roles;
    }
}
