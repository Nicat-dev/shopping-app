package az.nicat.shoppingapp.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,updatable = false,insertable = false)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "price",nullable = false)
    private BigDecimal price;
    @Column(name = "stock",nullable = false)
    private Long stock;
    @Column(name = "description",nullable = false)
    private String description;


    @CreationTimestamp
    @DateTimeFormat(pattern = "yyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyy-MM-dd HH:mm")
    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyy-MM-dd HH:mm")
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    @ToString.Exclude
    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();

}
