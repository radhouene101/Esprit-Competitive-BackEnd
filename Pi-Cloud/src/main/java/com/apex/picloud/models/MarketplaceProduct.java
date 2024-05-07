package com.apex.picloud.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MarketplaceProducts")
@Data // Includes getters, setters, equals, and hashCode methods from Lombok
public class MarketplaceProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false) // Add `nullable = false` for mandatory fields
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_price", nullable = false)
    private double productPrice; // Consider using BigDecimal for precise monetary calculations



    @ManyToOne

    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @Column(name = "image_url")
    private String imageUrl; // Added attribute for image URL


    @Override
    public String toString() {
        return "MarketplaceProduct{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productPrice=" + productPrice +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
    // Constructors (optional with Lombok)
}