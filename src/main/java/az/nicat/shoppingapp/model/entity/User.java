package az.nicat.shoppingapp.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name = "id",updatable = false,insertable = false)
    private Long Id;

    @Column(name = "full_name",nullable = false)
    private String name;
    @Column(name = "user_name",nullable = false,unique = true)
    private String username;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Column(nullable = false, name = "password")
    private String password;

    @Column(name = "is_active",nullable = false)
    private Boolean isActive;
    @Column(name = "is_notlocked",nullable = false)
    private Boolean isNotLocked;

    @ManyToMany
    @ToString.Exclude
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_role_name"))
    private List<Role> roles = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @Column(name = "last_login_at")
    @DateTimeFormat(pattern = "yyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyy-MM-dd HH:mm")
    private LocalDateTime lastLoginAt;

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



}
