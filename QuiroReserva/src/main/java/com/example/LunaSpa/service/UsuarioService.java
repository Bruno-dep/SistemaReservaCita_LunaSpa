package com.example.LunaSpa.service;

import com.example.LunaSpa.model.Usuario;
import java.util.List;

public interface UsuarioService {

    Usuario buscarPorId(Long id);
    List<Usuario> listarTodos();
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    Usuario obtenerPorId(Long id);
    //corroborar ususario y contra
    Usuario validarUsuario(String username, String password);
    
} 
