package com.boots.service;

import com.boots.entity.Role;
import com.boots.entity.Sign;
import com.boots.entity.User;
import com.boots.repository.RoleRepository;
import com.boots.repository.SignRepository;
import com.boots.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SignRepository signRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public Long getCurrentId(){
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }

    public List<Sign> allSigns() {
        Long user = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()).getId();

        return em.createQuery("SELECT u FROM Sign u WHERE u.user = :paramId", Sign.class)
                .setParameter("paramId", userRepository.findById(user).orElse(new User())).getResultList();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean saveSign(Sign sign) {
        User userFromDB = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());

        sign.setUser(userFromDB);
        signRepository.save(sign);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
    @Transactional
    public boolean update(Long id, String name, String new_password) {

        User userFromDB = userRepository.findByUsername(name);

        if (userFromDB != null && !name.contains(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return false;
        }
        if (findUserById(getCurrentId()).getPassword().contains(new_password))
        {
        em.createQuery("UPDATE User p SET p.username = ?2, p.password = ?3 WHERE p.id = ?1")
                .setParameter(1, id).setParameter(2, name).setParameter(3, new_password)
                .executeUpdate();}
        else {
            em.createQuery("UPDATE User p SET p.username = ?2, p.password = ?3 WHERE p.id = ?1")
                    .setParameter(1, id).setParameter(2, name).setParameter(3, bCryptPasswordEncoder.encode(new_password))
                    .executeUpdate();
        }

        return true;
    }

}
