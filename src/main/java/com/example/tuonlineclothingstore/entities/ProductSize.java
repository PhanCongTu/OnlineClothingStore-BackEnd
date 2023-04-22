package com.example.tuonlineclothingstore.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "product_size")
public class ProductSize {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String size;

    private Date createAt = new Date(new java.util.Date().getTime());

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}
