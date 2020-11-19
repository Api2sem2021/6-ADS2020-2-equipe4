package br.gov.sp.fatec.springbootapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import br.gov.sp.fatec.springbootapp.entity.Mensagem;
import br.gov.sp.fatec.springbootapp.service.ChatService;

@Controller
@CrossOrigin
public class ConversaController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/hello")
    // @SendTo("/topic/greetings")
    // @JsonView(View.MensagemResumo.class)
    public void greeting(@RequestBody ObjectNode body) throws Exception {
        Thread.sleep(1000); // simulated delay
        // String nomeRemetente = body.get("nomeRemetente").asText();
        // String nomeDestinatario = body.get("nomeDestinatario").asText();

        // Mensagem mensagem = chatService.enviarMensagem(body.get("chatID").asLong(), nomeRemetente, nomeDestinatario,
        //         body.get("dataHora").asText(), body.get("conteudo").asText());
        // ObjectMapper mapper = new ObjectMapper();
        // String result = mapper
        // .writerWithView(View.MensagemResumo.class)
        // .writeValueAsString(mensagem);
        // messagingTemplate.convertAndSend("/topic/greetings/1",result);
        //  return mensagem;
    }
}
