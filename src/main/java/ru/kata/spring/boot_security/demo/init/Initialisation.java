package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDetailInterface;
import ru.kata.spring.boot_security.demo.service.UserDetailServiceImp;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Initialisation {

    private UserDetailInterface userDetailInterface;

    @Autowired
    public Initialisation(UserDetailServiceImp userDetailServiceImp) {
        this.userDetailInterface = userDetailServiceImp;
    }

    @PostConstruct
    public void init() {
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");

        List<Role> roleList = new ArrayList<>();
        List<Role> roleList1 = new ArrayList<>();
        roleList.add(role1);
        roleList1.add(role2);

        User user = new User("user@mail.com", "100", "user", 20, "user");
        user.addRole(roleList1);
        User admin = new User("admin@mail.com", "100", "admin", 24, "admin");
        admin.addRole(roleList);

        userDetailInterface.saveUser(admin);
        userDetailInterface.saveUser(user);
    }


}
