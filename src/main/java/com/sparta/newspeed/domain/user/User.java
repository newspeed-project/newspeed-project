package com.sparta.newspeed.domain.user;

import com.sparta.newspeed.domain.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean active = true;

    private LocalDateTime deleteDate;

    public void update(String newPassword, String newUsername) {
        this.password = newPassword;
        this.username = newUsername;
    }

    public void delete() {
        this.deleteDate = LocalDateTime.now();
        this.active = false;
    }
}
