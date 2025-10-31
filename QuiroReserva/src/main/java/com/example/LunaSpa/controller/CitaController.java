package com.example.LunaSpa.controller;

import com.example.LunaSpa.model.Cita;
import com.example.LunaSpa.model.Paciente;
import com.example.LunaSpa.model.Doctor;
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
@RequestMapping("/cita")
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
        model.addAttribute("titulo", "Gestión de Citas");
        model.addAttribute("contenido", "cita/lista");
        model.addAttribute("cita", new Cita());
        model.addAttribute("pacientes", pacienteService.listarTodos());
        model.addAttribute("doctores", doctorService.listarTodos());
        return "layout";
    }

    @PostMapping("/guardar")
    public String guardar(@Validated @ModelAttribute("cita") Cita cita,
                          BindingResult result,
                          @RequestParam("paciente.id") Long pacienteId,
                          @RequestParam("doctor.id") Long doctorId,
                          RedirectAttributes redirectAttributes,
                          Model model) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Error en el formulario de la cita.");
            return "redirect:/cita";
        }

        Paciente paciente = pacienteService.buscarPorId(pacienteId);
        Doctor doctor = doctorService.buscarPorId(doctorId);

        if (paciente == null || doctor == null) {
            redirectAttributes.addFlashAttribute("error", "Paciente o doctor seleccionado inválido.");
            return "redirect:/cita";
        }
        cita.setEstado(cita.getEstado());
        cita.setPaciente(paciente);
        cita.setDoctor(doctor);

        try {
            citaService.guardar(cita);
            redirectAttributes.addFlashAttribute("success", "Cita registrada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la cita: " + e.getMessage());
        }

        return "redirect:/cita";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        citaService.eliminar(id);
        redirectAttributes.addFlashAttribute("success", "Cita eliminada correctamente.");
        return "redirect:/cita";
    }
}
