package br.gov.sp.fatec.springbootapp.controller;

import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.springbootapp.entity.Conversa;
import br.gov.sp.fatec.springbootapp.entity.Mensagem;
import br.gov.sp.fatec.springbootapp.repository.MensagemRepository;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;
import br.gov.sp.fatec.springbootapp.service.ChatService;

@RestController
@RequestMapping(value = "/chat")
@CrossOrigin
public class ChatController {

    @Autowired
    UsuarioRepository usuarioRepo;

    @Autowired
    ChatService chatService;

    @Autowired
    MensagemRepository mensagemRepo;

    @PostMapping(value = "/criarConversa")
    // @PreAuthorize("isAuthenticated()")
    public Conversa iniciarConversa() {
        Conversa conversa = chatService.iniciarConversa();

        return conversa;
    }

    @JsonView(View.ConversaResumo.class )
    @GetMapping(value = "/buscarPorId")
    // @PreAuthorize("isAuthenticated()")
    public Conversa buscarConversaPorId(@RequestParam("id") String id) {
        Conversa conversa = chatService.buscarConversaPorId(new Long(id));

        return conversa;
    }

    @JsonView(View.MensagemResumo.class)
    @PostMapping(value = "/enviarMensagem")
    public Mensagem enviarMensagem(@RequestBody ObjectNode body) {
        Mensagem mensagem = chatService.enviarMensagem(body.get("id").asLong(), null,null,null,1L,null,null);

        return mensagem;
    }

    @JsonView(View.MensagemResumo.class)
    @PostMapping(value = "/buscarMensagem")
    public Mensagem buscarMensagem(@RequestBody ObjectNode body) {
         Optional<Mensagem> mensagem = mensagemRepo.findById(body.get("id").asLong());

        return mensagem.get();
    }

    @JsonView(View.MensagemResumo.class)
    @GetMapping(value = "/buscarMensagemAtivas")
    public Set<Mensagem> buscarMensagensAtivas() {

        return mensagemRepo.buscarMensagensAtivas();
    }

}
