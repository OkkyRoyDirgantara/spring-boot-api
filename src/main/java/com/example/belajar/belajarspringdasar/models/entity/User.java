package com.example.belajar.belajarspringdasar.models.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required")
    @Column(nullable = false)
    @Size(min = 3, max = 255)
    private String name;

    @NotEmpty(message = "Email is required")
    @Column(nullable = false)
    @Email(message = "Email is not valid")
    @Size(max = 255, message = "Email is too long")
    private String email;

    @NotEmpty(message = "Password is required")
    @Column(nullable = false)
    @Size(min = 8, max = 16 , message = "Password must be between 8 and 16 characters")
    private String password;


    @CreationTimestamp
    private Date created_at;
    @UpdateTimestamp
    private Date updated_at;
}

