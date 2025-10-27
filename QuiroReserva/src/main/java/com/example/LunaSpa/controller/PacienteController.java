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
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pacientes", pacienteService.listarTodos());
        return "paciente/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "paciente/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Validated @ModelAttribute("paciente") Paciente paciente,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("paciente", paciente);
            model.addAttribute("error", "Por favor, corrige los errores en el formulario.");
            return "paciente/formulario";
        }
        try {
            pacienteService.guardar(paciente);
            String mensaje = (paciente.getId() == null) ? "Paciente registrado correctamente" : "Paciente actualizado correctamente";
            redirectAttributes.addFlashAttribute("success", mensaje);
        } catch (Exception e) {
            model.addAttribute("paciente", paciente);
            model.addAttribute("error", "Error al guardar el paciente: " + e.getMessage());
            return "paciente/formulario";
        }
        return "redirect:/pacientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Paciente paciente = pacienteService.buscarPorId(id);
        if (paciente == null) {
            redirectAttributes.addFlashAttribute("error", "El paciente no existe.");
            return "redirect:/pacientes";
        }
        model.addAttribute("paciente", paciente);
        return "paciente/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Paciente paciente = pacienteService.buscarPorId(id);
        if (paciente == null) {
            redirectAttributes.addFlashAttribute("error", "El paciente no existe.");
        } else {
            pacienteService.eliminar(id);
            redirectAttributes.addFlashAttribute("success", "Paciente eliminado correctamente.");
        }
        return "redirect:/pacientes";
    }
}