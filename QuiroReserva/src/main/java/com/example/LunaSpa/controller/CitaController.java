package com.example.LunaSpa.controller;

import com.example.LunaSpa.model.Cita;
import com.example.LunaSpa.service.CitaService;
import com.example.LunaSpa.service.PacienteService;
import com.example.LunaSpa.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "cita/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("pacientes", pacienteService.listarTodos());
        model.addAttribute("doctores", doctorService.listarTodos());
        return "cita/formulario";
    }

    @PostMapping
    public String guardar(@Validated @ModelAttribute("cita") Cita cita, BindingResult result,
                          Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pacientes", pacienteService.listarTodos());
            model.addAttribute("doctores", doctorService.listarTodos());
            return "cita/formulario";
        }
        try {
            citaService.guardar(cita);
            redirectAttributes.addFlashAttribute("success", "Cita guardada correctamente.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("pacientes", pacienteService.listarTodos());
            model.addAttribute("doctores", doctorService.listarTodos());
            return "cita/formulario";
        }
        return "redirect:/citas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Cita cita = citaService.buscarPorId(id);
        if (cita == null) {
            redirectAttributes.addFlashAttribute("error", "La cita no existe.");
            return "redirect:/citas";
        }
        model.addAttribute("cita", cita);
        model.addAttribute("pacientes", pacienteService.listarTodos());
        model.addAttribute("doctores", doctorService.listarTodos());
        return "cita/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (citaService.buscarPorId(id) == null) {
            redirectAttributes.addFlashAttribute("error", "La cita no existe.");
        } else {
            citaService.eliminar(id);
            redirectAttributes.addFlashAttribute("success", "Cita eliminada correctamente.");
        }
        return "redirect:/citas";
    }
}