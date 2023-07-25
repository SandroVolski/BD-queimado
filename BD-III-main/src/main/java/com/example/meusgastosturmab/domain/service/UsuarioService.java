package com.example.meusgastosturmab.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.meusgastosturmab.domain.dto.usuario.UsuarioRequestDTO;
import com.example.meusgastosturmab.domain.dto.usuario.UsuarioResponseDTO;
import com.example.meusgastosturmab.domain.exception.BadRequestException;
import com.example.meusgastosturmab.domain.exception.ResourceNotFoundException;
import com.example.meusgastosturmab.domain.repository.UsuarioRepository;
import com.example.meusgastosturmab.domain.model.Usuario;

@Service
public class UsuarioService implements ICRUDService<UsuarioRequestDTO, UsuarioResponseDTO>{

    @Autowired //permite usar as variaveis sem precisar instanciar elas
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioResponseDTO> obterTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll(); //lista de usuarios q o banco vai retornar
        return usuarios.stream()//estrutura de repeticao(for,while...)
        .map(usuario -> mapper.map(usuario, UsuarioResponseDTO.class))//transforma a variavel usuario em UsuarioResponseDto, convertendo cada instancia da variavel usuario
        .collect(Collectors.toList());//colecta e retorna a lista de usuariosResponseDTO
    }
    @Override
    public UsuarioResponseDTO obterPorId(Long id) {
        //Optional, aceita n retornar nada, caso n tenha nada prenchido no banco
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        if(!optUsuario.isPresent()){
            //lancar uma excecao
            throw new ResourceNotFoundException("Nao foi possivel encontrar o usuario com o id:" + id);
        }
        return mapper.map(optUsuario.get(),UsuarioResponseDTO.class);
    }

    @Override
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if(dto.getEmail() == null || dto.getSenha() == null){
            throw new BadRequestException("Email e Senha sao obrigatorios!");
        }
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(dto.getEmail());
        if(optUsuario.isPresent()){//verifica se email e unico
            throw new BadRequestException("Já existe um usuario com o email: " + dto.getEmail());
        }
        Usuario usuario = mapper.map(dto, Usuario.class);
        usuario.setDataCadastro(new Date());
        //encriptografar senha
        String senha = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senha);
        usuario.setId(null);
        usuario = usuarioRepository.save(usuario);// assim que salva o usuario no banco
        return mapper.map(usuario, UsuarioResponseDTO.class);
        //OU
        /*
        Usuario usuarioretorno = usuarioRepository.save(usuario);// assim que salva o usuario no banco
        return mapper.map(usuarioretorno, UsuarioResponseDTO.class);
         */
    }

    @Override
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {// atualizar = salvar
        UsuarioResponseDTO usuarioBanco = obterPorId(id);//obtem id
        if(dto.getEmail() == null || dto.getSenha() == null){
            throw new BadRequestException("Email e Senha sao obrigatorios!");
        }
        Usuario usuario = mapper.map(dto, Usuario.class);
        usuario.setSenha(dto.getSenha());
        usuario.setId(id);//altera os dados do usuario desse id, sem precisar criar outro usuario com outro id
        usuario.setDataCadastro(usuarioBanco.getDataCadastro());
        usuario.setDataInativacao(usuarioBanco.getDataInativacao());
        usuario = usuarioRepository.save(usuario);// assim que salva o usuario no banco
        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Override
    public void deletar(Long id) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        if(!optUsuario.isPresent()){
            throw new ResourceNotFoundException("Nao foi possivel encontrar o usuario com o id:" + id);
        }
        Usuario usuario = optUsuario.get(); //pega o usuario
        usuario.setDataInativacao(new Date());//data de inativacao
        usuarioRepository.save(usuario);//salva essa data de inativacao no usuario

        //APAGA DA BASE(BD)
        /*obterPorId(id);
        usuarioRepository.deleteById(id);*/
    }
    
}
