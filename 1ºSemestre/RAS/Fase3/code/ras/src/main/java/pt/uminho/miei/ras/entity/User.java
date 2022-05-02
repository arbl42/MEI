package pt.uminho.miei.ras.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(name = "username", length = 64, nullable = false, unique = true)
    @Size(min=2, max=30, message = "Nome inválido")
    private String username;

    @Column(name = "email", length = 64, nullable = false, unique = true)
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email inválido")
    private String email;

    @Column(name = "password", length = 64, nullable = false)
    @Size(min=2, max=30, message = "Password inválida")
    private String password;

    @Column(name = "wallet", length = 64, nullable = false)
    @Digits(integer = 8, fraction = 2, message = "Formato inválido")
    @DecimalMin(value = "0.00", message = "O valor deve ser positivo")
    private BigDecimal wallet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getWallet() {
        return wallet;
    }

    public void setWallet(BigDecimal wallet) {
        this.wallet = wallet;
    }
}
