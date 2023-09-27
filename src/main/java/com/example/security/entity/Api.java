package com.example.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "apis")
public class Api implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long apiId;

    @Column(unique = true)
    private String apiCode;

    @Column(unique = true)
    private String apiAddress;

    @ManyToMany(mappedBy = "listApi")
    @Fetch(value = FetchMode.SELECT)
    @JsonIgnore
    private Set<Permission> permissions = new HashSet<>();
}
