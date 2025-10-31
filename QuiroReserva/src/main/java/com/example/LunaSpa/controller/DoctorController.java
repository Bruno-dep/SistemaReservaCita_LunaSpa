package com.example.LunaSpa.controller;

import com.example.LunaSpa.model.Doctor;
import com.example.LunaSpa.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("doctores", doctorService.listarTodos());
        model.addAttribute("titulo", "Gestión de Doctores");
        model.addAttribute("contenido", "doctor/lista");
        model.addAttribute("doctor", new Doctor());
        return "layout";
    }

    @PostMapping("/guardar")
    public String guardar(@Validated @ModelAttribute("doctor") Doctor doctor,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el doctor.");
            return "redirect:/doctor";
        }
        doctorService.guardar(doctor);
        redirectAttributes.addFlashAttribute("success", "Doctor registrado correctamente.");
        return "redirect:/doctor";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Doctor doctor = doctorService.obtenerPorId(id);
        if (doctor == null) {
            redirectAttributes.addFlashAttribute("error", "Doctor no encontrado.");
            return "redirect:/doctor";
        }
        model.addAttribute("doctor", doctor);
        model.addAttribute("titulo", "Editar Doctor");
        model.addAttribute("contenido", "doctor/editar");
        return "layout";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        doctorService.eliminar(id);
        redirectAttributes.addFlashAttribute("success", "Doctor eliminado correctamente.");
        return "redirect:/doctor";
    }
}
