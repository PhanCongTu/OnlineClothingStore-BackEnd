package com.example.tuonlineclothingstore.entities;

import com.example.tuonlineclothingstore.utils.EnumOrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Date;

@Entity
@Data
@Table(name = "order")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    private String address;

    private String phoneNumber;

    private String note;

    private String status;

    private double total;

    private Date createAt = new Date(new java.util.Date().getTime());

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL
    )
    private List<OrderItem> orderItems;
}
