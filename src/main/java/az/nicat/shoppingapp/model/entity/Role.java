package az.nicat.shoppingapp.model.entity;

import az.nicat.shoppingapp.model.enums.ERole;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role implements Serializable {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name",nullable = false,unique = true)
    private ERole roleName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return roleName != null && Objects.equals(roleName, role.roleName);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
