package com.example.tuonlineclothingstore.entities;

import com.example.tuonlineclothingstore.utils.Role;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
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
    List<Role> roles;

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

}
