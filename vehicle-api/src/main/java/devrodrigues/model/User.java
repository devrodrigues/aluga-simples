package devrodrigues.model;

import devrodrigues.model.enums.UserRole;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Optional;

import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.find;

@Entity
@Table(name = "app_user")
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password; //armazenar com Bcrypt
    private String nome;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public User(){}

    public User(String username, String password, String nome, String email, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.email = email;
        this.userRole = userRole;
    }

    public static Optional<User> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
