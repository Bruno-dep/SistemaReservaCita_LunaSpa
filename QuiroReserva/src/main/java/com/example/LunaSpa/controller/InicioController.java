package com.example.LunaSpa.controller;

import com.example.LunaSpa.service.CitaService;
import com.example.LunaSpa.service.DoctorService;
import com.example.LunaSpa.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private CitaService citaService;

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("titulo", "Inicio - LunaSpa");
        model.addAttribute("contenido", "inicio");
        model.addAttribute("pacientesCount", pacienteService.listarTodos().size());
        model.addAttribute("doctoresCount", doctorService.listarTodos().size());
        model.addAttribute("citasCount", citaService.listarTodas().size());
        return "layout";
    }
}