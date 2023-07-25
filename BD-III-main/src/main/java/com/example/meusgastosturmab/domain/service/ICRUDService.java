package com.example.meusgastosturmab.domain.service;

import java.util.List;

public interface ICRUDService<Request, Response> { //puxando/pedindo/cadastro , o q retorna/ devolve ao pedido da acao 
    List<Response> obterTodos();
    Response obterPorId(Long id);
    Response cadastrar(Request dto);
    Response atualizar(Long id, Request dto); //dto(objeto de tranferencia)
    void deletar(Long id);
}
