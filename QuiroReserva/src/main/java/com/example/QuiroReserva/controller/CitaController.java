package com.example.QuiroReserva.controller;

import com.example.QuiroReserva.model.Cita;
import com.example.QuiroReserva.service.CitaService;
import com.example.QuiroReserva.service.PacienteService;
import com.example.QuiroReserva.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("listaCitas", citaService.listarTodas());
        return "citas/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("pacientes", pacienteService.listarTodos());
        model.addAttribute("doctores", doctorService.listarTodos());
        return "citas/formulario";
    }

    @PostMapping
    public String guardar(@Validated @ModelAttribute("cita") Cita cita) {
        citaService.guardar(cita);
        return "redirect:/citas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("cita", citaService.buscarPorId(id));
        model.addAttribute("pacientes", pacienteService.listarTodos());
        model.addAttribute("doctores", doctorService.listarTodos());
        return "citas/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return "redirect:/citas";
    }
}
