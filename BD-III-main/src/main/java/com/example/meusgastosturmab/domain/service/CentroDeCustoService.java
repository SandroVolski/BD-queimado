package com.example.meusgastosturmab.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.meusgastosturmab.domain.dto.centrodecusto.CentroDeCustoRequestDTO;
import com.example.meusgastosturmab.domain.dto.centrodecusto.CentroDeCustoResponseDTO;
import com.example.meusgastosturmab.domain.exception.ResourceNotFoundException;
import com.example.meusgastosturmab.domain.model.CentroDeCusto;
import com.example.meusgastosturmab.domain.model.Usuario;
import com.example.meusgastosturmab.domain.repository.CentroDeCustoRepository;

@Service
public class CentroDeCustoService implements ICRUDService<CentroDeCustoRequestDTO, CentroDeCustoResponseDTO>{

    @Autowired
    private CentroDeCustoRepository centroDeCustoRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<CentroDeCustoResponseDTO> obterTodos() {
        Usuario usuario = (Usuario) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();
        List<CentroDeCusto> lista = centroDeCustoRepository.findByUsuario(usuario);
        return lista.stream() //"for", passa todos os dados para aparecer todos de uma vez
        .map(centroDeCusto -> mapper.map(centroDeCusto,
        CentroDeCustoResponseDTO.class))
        .collect(Collectors.toList());
    }

    @Override
    public CentroDeCustoResponseDTO obterPorId(Long id) {
        Optional<CentroDeCusto> optCentroDecCusto = 
        centroDeCustoRepository.findById(id);
        if(optCentroDecCusto.isEmpty()){
            throw new ResourceNotFoundException("Não foi possivel encontrar o centro de Custo com o id: "+ id);
        }
        return mapper.map(optCentroDecCusto.get(), CentroDeCustoResponseDTO.class);
    }

    @Override
    public CentroDeCustoResponseDTO cadastrar(CentroDeCustoRequestDTO dto) {
        CentroDeCusto centroDeCusto = mapper.map(dto, CentroDeCusto.class);
        Usuario usuario = (Usuario) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();
        centroDeCusto.setUsuario(usuario);
        centroDeCusto.setId(null); // garante q n tenha o id, está CADASTRANDO, não atualizando(já possui id)
        centroDeCusto = centroDeCustoRepository.save(centroDeCusto);
        return mapper.map(centroDeCusto, CentroDeCustoResponseDTO.class);
    }

    @Override
    public CentroDeCustoResponseDTO atualizar(Long id, CentroDeCustoRequestDTO dto) {
        obterPorId(id);
        CentroDeCusto centroDeCusto = mapper.map(dto, CentroDeCusto.class);
        Usuario usuario = (Usuario) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();
        centroDeCusto.setUsuario(usuario);
        centroDeCusto.setId(id); // garante q n tenha o id, está CADASTRANDO, não atualizando(já possui id)
        centroDeCusto = centroDeCustoRepository.save(centroDeCusto);
        return mapper.map(centroDeCusto, CentroDeCustoResponseDTO.class);
    }

    @Override
    public void deletar(Long id) {
        obterPorId(id);
        centroDeCustoRepository.deleteById(id);
    }
    
}