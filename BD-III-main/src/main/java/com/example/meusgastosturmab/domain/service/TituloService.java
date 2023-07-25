package com.example.meusgastosturmab.domain.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.ResourceReaderRepositoryPopulatorBeanDefinitionParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.meusgastosturmab.domain.dto.titulo.TituloRequestDTO;
import com.example.meusgastosturmab.domain.dto.titulo.TituloResponseDTO;
import com.example.meusgastosturmab.domain.exception.ResourceNotFoundException;
import com.example.meusgastosturmab.domain.model.Titulo;
import com.example.meusgastosturmab.domain.model.Usuario;

@Service
public class TituloService implements ICRUDService {

    @Autowired
    private TituloRepository tituloRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List <TituloResponseDTO> obterTodos() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List <Titulo> titulos = tituloRepository.findByUsuario(usuario);
        return titulos.stream()
        .map(titulo -> mapper.map(titulo, TituloResponseDTO.class))
        .collect(Collectors.toList());
    }

    @Override
    public TituloResponseDTO obterPorId(Long id) {
        Optional<Titulo> optTitulo = tituloRepository.findById(id);
        if (optTitulo.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possivel encontrar o titulo com o id: " + id);
        }
        return mapper.map(optTitulo, TituloResponseDTO);
    }

    @Override
    public TituloResponseDTO cadastrar(TituloRequestDTO dto) {
        validarTitulo(dto);
        Titulo titulo = mapper.map(dto, Titulo.class)
        Usuario usuario = (Usuario)
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        titulo.setUsuario(usuario);
        titulo.setId(null);
        titulo.setDataCadastro(new Date());
        titulo = tituloRepository.save(titulo);
        return mapper.map(titulo, TituloResponseDTO.class);
    }

    @Override
    public TituloResponseDTO atualizar(Long id, TituloRequestDTO dto) {
        obterPorId(id);
        validarTitulo(dto);
        Titulo titulo = mapper.map(dto, Titulo.class)
        Usuario usuario = (Usuario)
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        titulo.setUsuario(usuario);
        titulo.setId(id);
        titulo = tituloRepository.save(titulo);
        return mapper.map(titulo, TituloResponseDTO.class);
    }

    @Override
    public void deletar(Long id) {
        obterPorId(id);
        tituloRepository.deleteById(id);
    }

    private void validarTitulo(TituloRequestDTO dto){
        if (dto.getTipo() == null || dto.getDataVencimento() == null || dto.getValor() == null || dto.getDescricao() == null) {
            throw new ResourceBadRequestException("Titulo inválido - Campos obrigatórios!");
        }
    }
}
