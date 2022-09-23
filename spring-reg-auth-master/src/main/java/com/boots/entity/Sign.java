package com.boots.entity;

import com.boots.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "t_sign")
public class Sign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    private String doctor;

    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Sign() {
    }

    public Sign(Long id) {
        this.id = id;
    }

    public Sign(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Sign(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Sign(Long id, String name, String email, String doctor) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.doctor = doctor;
    }

    public Sign(Long id, String name, String email, String doctor, String text, User user) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.doctor = doctor;
        this.text = text;
        this.user = user;
    }

    public Sign(String name, String email, String doctor, String text, User user) {
        this.name = name;
        this.email = email;
        this.doctor = doctor;
        this.text = text;
        this.user = user;
    }

    public Sign(Long id, String name, String email, String doctor, String text) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.doctor = doctor;
        this.text = text;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
