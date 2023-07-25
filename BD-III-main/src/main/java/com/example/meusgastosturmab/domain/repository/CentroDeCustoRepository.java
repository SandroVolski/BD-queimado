package com.example.meusgastosturmab.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.meusgastosturmab.domain.model.CentroDeCusto;
import com.example.meusgastosturmab.domain.model.Usuario;

public interface CentroDeCustoRepository extends JpaRepository<CentroDeCusto, Long>{
    
    List<CentroDeCusto> findByUsuario(Usuario usuario);
}
