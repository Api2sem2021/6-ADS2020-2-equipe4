package br.gov.sp.fatec.springbootapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin // todos os metodos vao permitir acesso externo 
public class HomeController {
    
    @GetMapping // responde a uma requisicao do tipo Get direto no navegador
    public String welcome(){
        return "Hello World";
    }
    
}