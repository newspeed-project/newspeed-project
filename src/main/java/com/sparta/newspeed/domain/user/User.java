package com.sparta.newspeed.domain.user;

import com.sparta.newspeed.domain.TimeStamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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

    @Enumerated(EnumType.STRING) // 역할을 문자열로 저장
    private UserRole role;

    @Contract(pure = true)
    public UserRole getRole() {
        return null;
    }

    public void setEmail(@NotBlank(message = "이메일은 비어있을 수 없습니다.") @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") @Email(message = "유효한 이메일 형식이 아닙니다.") String email) {
    }

    public void setPassword(String encodedPassword) {
    }

    public void setUsername(@NotBlank(message = "유저명은 비어있을 수 없습니다.") @Size(max = 10, message = "유저명은 최대 10글자까지 가능합니다.") String username) {
    }

    public void setRole(UserRole userRole) {
    }
}
