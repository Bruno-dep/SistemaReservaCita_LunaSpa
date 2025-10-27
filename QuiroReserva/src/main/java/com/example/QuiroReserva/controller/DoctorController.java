package com.example.QuiroReserva.controller;

import com.example.QuiroReserva.model.Doctor;
import com.example.QuiroReserva.service.DoctorService;
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

    @PostMapping("/guardar")
    public String guardar(@Validated @ModelAttribute("doctor") Doctor doctor,
                          BindingResult result,
                          RedirectAttributes redirectAttributes,
                          Model model) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario Doctor");
            model.addAttribute("contenido", "doctor/formulario");
            return "layout";
        }

        doctorService.guardar(doctor);

        redirectAttributes.addFlashAttribute("success", "Doctor guardado correctamente");
        return "redirect:/doctores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctorService.buscarPorId(id));
        model.addAttribute("titulo", "Editar Doctor");
        model.addAttribute("contenido", "doctor/formulario");
        return "layout";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        doctorService.eliminar(id);
        ra.addFlashAttribute("success", "Doctor eliminado correctamente");
        return "redirect:/doctores";
    }
}
