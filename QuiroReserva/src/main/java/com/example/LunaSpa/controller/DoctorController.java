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
@RequestMapping("/doctores")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("doctores", doctorService.listarTodos());
        model.addAttribute("titulo", "Doctores");
        model.addAttribute("contenido", "doctor/lista");
        return "layout";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("titulo", "Nuevo Doctor");
        model.addAttribute("contenido", "doctor/formulario");
        return "layout";
    }

    @PostMapping
    public String guardar(@Validated @ModelAttribute("doctor") Doctor doctor,
                          BindingResult result,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario Doctor");
            model.addAttribute("contenido", "doctor/formulario");
            model.addAttribute("error", "Por favor, corrige los errores en el formulario.");
            return "layout";
        }
        try {
            doctorService.guardar(doctor);
            String mensaje = (doctor.getId() == null) ? "Doctor registrado correctamente" : "Doctor actualizado correctamente";
            redirectAttributes.addFlashAttribute("success", mensaje);
        } catch (Exception e) {
            model.addAttribute("titulo", "Formulario Doctor");
            model.addAttribute("contenido", "doctor/formulario");
            model.addAttribute("error", "Error al guardar el doctor: " + e.getMessage());
            return "layout";
        }
        return "redirect:/doctores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Doctor doctor = doctorService.buscarPorId(id);
        if (doctor == null) {
            redirectAttributes.addFlashAttribute("error", "El doctor no existe.");
            return "redirect:/doctores";
        }
        model.addAttribute("doctor", doctor);
        model.addAttribute("titulo", "Editar Doctor");
        model.addAttribute("contenido", "doctor/formulario");
        return "layout";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Doctor doctor = doctorService.buscarPorId(id);
        if (doctor == null) {
            redirectAttributes.addFlashAttribute("error", "El doctor no existe.");
        } else {
            doctorService.eliminar(id);
            redirectAttributes.addFlashAttribute("success", "Doctor eliminado correctamente.");
        }
        return "redirect:/doctores";
    }
}