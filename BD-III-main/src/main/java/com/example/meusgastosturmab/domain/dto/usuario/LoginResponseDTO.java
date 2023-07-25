package com.example.meusgastosturmab.domain.dto.usuario;

public class LoginResponseDTO {
    private String Token;
    private UsuarioResponseDTO usuario;

    public String getToken(){
        return Token;
    }
    public void setToken(String token){
        this.Token = token;
    }
    public UsuarioResponseDTO getUsuario(){
        return usuario;
    }
    public void setUsuario(UsuarioResponseDTO usuario){
        this.usuario = usuario;
    }
}
