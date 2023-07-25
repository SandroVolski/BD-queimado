package com.example.meusgastosturmab.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.meusgastosturmab.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{ //<usuario, idUsuario>
    Optional<Usuario> findByEmail(String email); // verifica se ja existe um usuario com esse email
}
