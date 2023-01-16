package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserDetailServiceImp;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserDetailServiceImp userDetailServiceImp;
    private final RoleService roleService;

    public AdminController(UserDetailServiceImp userDetailServiceImp, RoleService roleService) {
        this.userDetailServiceImp = userDetailServiceImp;
        this.roleService = roleService;
    }


    @GetMapping
    public String listUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("users", userDetailServiceImp.allUser());
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
        userDetailServiceImp.saveUser(user);
        return "redirect:/admin";

    }

//    @GetMapping("/edit/{id}")
//    public ModelAndView editUser(@PathVariable(name = "id") Long id) {
//        User user = userDetailServiceImp.findUserById(id);
//        ModelAndView model = new ModelAndView("edit");
//        model.addObject("user", user);
//        List<Role> roles = roleService.findAllRoles();
//        model.addObject("allRoles", roles);
//
//        return model;
//    }

    @PutMapping ("edit/{id}")
    public String update(@ModelAttribute("user") User user,  @RequestParam(name = "listRoles") Long role) {
        user.addRole(roleService.findRolesById(role));
        userDetailServiceImp.update(user);
        return "redirect:/admin";

    }

    @DeleteMapping("/deleteUser/{id}")
    public String delete(@PathVariable("id") Long id) {
        userDetailServiceImp.deleteUser(id);
        return "redirect:/admin";
    }
}

