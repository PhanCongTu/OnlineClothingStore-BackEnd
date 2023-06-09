package com.example.tuonlineclothingstore.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Date;

@Entity
@Data
@Table(name = "category")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String image;

    private Boolean isDeleted = false;

    @Column(nullable = false)
    private Date createAt= new Date(new java.util.Date().getTime());

    @Column
    private Date updateAt= new Date(new java.util.Date().getTime());

    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL
    )
    private List<Product> products ;
}
