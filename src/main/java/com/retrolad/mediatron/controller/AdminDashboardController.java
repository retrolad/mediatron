package com.retrolad.mediatron.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @GetMapping
    public String dashboard(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("admin", name);
        return "admin/dashboard";
    }

    @GetMapping("/main")
    public String mainContent(Model model) {
        return "admin/fragments :: main";
    }
}
