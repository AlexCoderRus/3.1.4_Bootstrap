package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceInterface;
import ru.kata.spring.boot_security.demo.service.UserDetailInterface;
import ru.kata.spring.boot_security.demo.service.UserDetailServiceImp;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserDetailInterface userDetailInterface;
    private final RoleServiceInterface roleService;

    public AdminController(UserDetailServiceImp userDetailServiceImp, RoleService roleService) {
        this.userDetailInterface = userDetailServiceImp;
        this.roleService = roleService;
    }


    @GetMapping
    public String listUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("users", userDetailInterface.allUser());
        model.addAttribute("roles", roleService.findAllRoles());
        return "users";
    }

    @GetMapping("/new")
    public String newUserEntity(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("newUser", new User());
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("roles", roles);
        return "new";
    }

    @PostMapping("/saveUser")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(name = "listRolesNew") Long role) {
        user.setRoles(roleService.findRolesById(role));
        userDetailInterface.saveUser(user);
        return "redirect:/admin";

    }

    @PutMapping ("edit/{id}")
    public String update(@ModelAttribute("user") User user,  @RequestParam(name = "listRoles") Long role) {
        user.addRole(roleService.findRolesById(role));
        userDetailInterface.update(user);
        return "redirect:/admin";

    }

    @DeleteMapping("/deleteUser/{id}")
    public String delete(@PathVariable("id") Long id) {
        userDetailInterface.deleteUser(id);
        return "redirect:/admin";
    }
}

