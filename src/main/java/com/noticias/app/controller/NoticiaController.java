package com.noticias.app.controller;

import com.noticias.app.service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoticiaController {

    @Autowired
    private NoticiaService service;

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("noticias", service.listarNoticias("us"));
        return "index";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam String pais, Model model) {
        model.addAttribute("noticias", service.listarNoticias(pais));
        return "index";
    }
}
