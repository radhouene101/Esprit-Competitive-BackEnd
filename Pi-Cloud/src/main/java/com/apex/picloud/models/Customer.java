package com.apex.picloud.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "full_name", nullable = false)
    private String Fullname;

    @Column(unique = true)
    private String Email;

    @Column(name = "adress")
    private String Adress;

    @Column(name = "phone")
    private String Phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Add this annotation to manage the JSON serialization
    private List<Order> orders;
}
