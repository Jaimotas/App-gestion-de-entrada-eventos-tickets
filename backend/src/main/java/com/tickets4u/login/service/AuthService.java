package com.tickets4u.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tickets4u.login.DTO.AuthResponse;
import com.tickets4u.login.DTO.LoginRequest;
import com.tickets4u.login.DTO.RegisterRequest;
import com.tickets4u.login.repository.UsuarioLoginRepository;
import com.tickets4u.models.Usuario;

@Service
public class AuthService {

    @Autowired
    private UsuarioLoginRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public void register(RegisterRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setEmail(request.getEmail());
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));

        // 🔒 SIEMPRE CLIENTE
        usuario.setRol(Usuario.Rol.CLIENTE);

        usuarioRepository.save(usuario);
    }

    public AuthResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(
                request.getContrasena(),
                usuario.getContrasena())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtService.generateToken(usuario);

        return new AuthResponse(token, usuario.getRol().name());
    }
}
