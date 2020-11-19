package br.gov.sp.fatec.springbootapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.springbootapp.security.JwtUtils;
import br.gov.sp.fatec.springbootapp.security.Login;
import br.gov.sp.fatec.springbootapp.service.SegurancaService;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    SegurancaService segService;


    @Autowired
    private AuthenticationManager authManager;

    @RequestMapping(value = "/login")
    @PostMapping()
    public Login autenticar(@RequestBody Login login) throws JsonProcessingException {
        Authentication auth = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        auth = authManager.authenticate(auth);
        
        login.setPassword(null);
        login.setToken(JwtUtils.generateToken(auth));
        login.setRefreshToken(JwtUtils.generateRefreshToken(auth));
        return login;
    }

    @RequestMapping(value = "/refresh")
    @GetMapping()
    public Login autenticar(Authentication auth) throws JsonProcessingException {

        Login login = new Login();
        login.setUsername(auth.getName());
        
        UserDetails usuario = segService.loadUserByUsername(login.getUsername());

        Authentication auth2 =  new UsernamePasswordAuthenticationToken(usuario.getUsername(), null,
                usuario.getAuthorities());

        login.setToken(JwtUtils.generateToken(auth2));
        login.setRefreshToken(JwtUtils.generateRefreshToken(auth2));
        return login;
    }
    
}