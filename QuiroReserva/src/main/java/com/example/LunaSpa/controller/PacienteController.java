package com.example.LunaSpa.controller;

import com.example.LunaSpa.model.Paciente;
import com.example.LunaSpa.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pacientes", pacienteService.listarTodos());
        model.addAttribute("titulo", "Gestión de Pacientes");
        model.addAttribute("contenido", "paciente/lista");
        model.addAttribute("paciente", new Paciente());
        return "layout";
    }

    @PostMapping("/guardar")
    public String guardar(@Validated @ModelAttribute("paciente") Paciente paciente,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el paciente.");
            return "redirect:/paciente";
        }

        pacienteService.guardar(paciente);
        redirectAttributes.addFlashAttribute("success", "Paciente registrado correctamente.");
        return "redirect:/paciente";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        pacienteService.eliminar(id);
        redirectAttributes.addFlashAttribute("success", "Paciente eliminado correctamente.");
        return "redirect:/paciente";
    }
}
