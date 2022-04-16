package az.nicat.shoppingapp.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_images")
public class ProductImage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,updatable = false,insertable = false)
    private Long id;

    @Column(name = "path",nullable = false)
    private String path;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "product_product_id")
    private Product product;



    @CreationTimestamp
    @DateTimeFormat(pattern = "yyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyy-MM-dd HH:mm")
    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

}
