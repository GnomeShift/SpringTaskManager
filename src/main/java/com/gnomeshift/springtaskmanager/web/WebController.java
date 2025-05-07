package com.gnomeshift.springtaskmanager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String showDashboardForm() {
        return "dashboard";
    }

    @GetMapping("/task/{id}/edit")
    public String showEditTaskForm() {
        return "edit_task";
    }

    @GetMapping("/admin")
    public String showAdminPanelForm() {
        return "admin_panel";
    }
}
