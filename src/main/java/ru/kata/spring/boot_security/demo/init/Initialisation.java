package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDetailServiceImp;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Initialisation {

    private UserDetailServiceImp userDetailServiceImp;

    @Autowired
    public Initialisation(UserDetailServiceImp userDetailServiceImp) {
        this.userDetailServiceImp = userDetailServiceImp;
    }

    @PostConstruct
    public void init() {
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");

        List<Role> roleList = new ArrayList<>();
        List<Role> roleList1 = new ArrayList<>();
        roleList.add(role1);
        roleList1.add(role2);
//
//        User user1 = new User("admin@mail.ru", "100", "admin", 35, "Adminov");
//        User user2 = new User("user@mail.ru", "100", "user", 24, "Userov");
//        User user3 = new User("milo@mail.ru", "100", "milo", 18, "Gorodnichev");
//        User user4 = new User("cat@mail.ru", "100", "cat", 35, "Stepanov");
//
//        user1.addRole(role2);
//        user2.addRole(role1);
//        user3.addRole(role2);
//        user4.addRole(role1);
//        userDetailServiceImp.saveUser(user1);
//        userDetailServiceImp.saveUser(user2);
//        userDetailServiceImp.saveUser(user3);
//        userDetailServiceImp.saveUser(user4);

        User user = new User("user@mail.com", "100", "user", 20, "user");
        user.addRole(roleList1);
        User admin = new User("admin@mail.com", "100", "admin", 24, "admin");
        admin.addRole(roleList);

        userDetailServiceImp.saveUser(admin);
        userDetailServiceImp.saveUser(user);
    }


}
