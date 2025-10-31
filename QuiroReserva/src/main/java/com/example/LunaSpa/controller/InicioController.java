package com.example.LunaSpa.controller;

import com.example.LunaSpa.model.Usuario;
import com.example.LunaSpa.service.CitaService;
import com.example.LunaSpa.service.DoctorService;
import com.example.LunaSpa.service.PacienteService;
import com.example.LunaSpa.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Controller
public class InicioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CitaService citaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/")
    public String mostrarDashboard(Model model) {
        long pacientesCount = pacienteService.listarTodos().size();
        long citasTotales = citaService.listarTodas().size();

        long citasHoy = citaService.listarTodas().stream()
                .filter(c -> c.getFechaHora().toLocalDate().equals(LocalDate.now()))
                .count();

        long pendientesCount = citaService.listarTodas().stream()
                .filter(c -> c.getEstado() != null && c.getEstado().equalsIgnoreCase("PENDIENTE"))
                .count();

        var proximasCitas = citaService.listarTodas().stream()
                .filter(c -> c.getFechaHora().isAfter(LocalDate.now().atStartOfDay()))
                .sorted((a, b) -> a.getFechaHora().compareTo(b.getFechaHora()))
                .limit(10)
                .collect(Collectors.toList());

        model.addAttribute("titulo", "Dashboard - LunaSpa");
        model.addAttribute("contenido", "dashboard");
        model.addAttribute("citasHoy", citasHoy);
        model.addAttribute("pacientesCount", pacientesCount);
        model.addAttribute("pendientesCount", pendientesCount);
        model.addAttribute("listaCitas", proximasCitas);
        model.addAttribute("citasCount", citasTotales);
        model.addAttribute("doctoresCount", doctorService.listarTodos().size());

        return "layout"; // sigue usando tu layout general
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute Usuario usuario,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        Usuario userDB = usuarioService.validarUsuario(usuario.getUsername(), usuario.getPassword());

        if (userDB != null) {
            session.setAttribute("usuarioLogueado", userDB);
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos.");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "Sesión cerrada correctamente.");
        return "redirect:/login";
    }
}
