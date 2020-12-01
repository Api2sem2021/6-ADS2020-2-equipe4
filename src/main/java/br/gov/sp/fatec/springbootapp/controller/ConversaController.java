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

    @MessageMapping("/messageHandler")
    public void greeting(@RequestBody ObjectNode body) throws Exception {
        Mensagem mensagem = new Mensagem();
        String origem = body.get("origem").asText();

        Long destinarioID = null;
        Long remetenteID = null;
        if(body.get("destinatarioID").isValueNode() )destinarioID = body.get("destinatarioID").asLong();
        if(body.get("remetenteID").isValueNode()) remetenteID = body.get("remetenteID").asLong(); 

        if (origem.equals("widget")) {
            mensagem = chatService.enviarMensagem(body.get("id").asLong(), body.get("conteudo").asText(),
                    body.get("nomeRemetente").asText(), null, null, null, body.get("data").asText(),
                    body.get("hora").asText());
        } 
        else if(origem.equals("painel")) {
            mensagem = chatService.enviarMensagem(body.get("id").asLong(), body.get("conteudo").asText(), body.get("nomeRemetente").asText(),
                    body.get("destinatarioNome").asText(), remetenteID,
                    destinarioID, body.get("data").asText(), body.get("hora").asText());
        }else{
            mensagem = chatService.enviarMensagem(body.get("id").asLong(), body.get("conteudo").asText(), null,
                null, body.get("remetenteID").asLong(), body.get("destinatarioID").asLong(), body.get("data").asText(),
                body.get("hora").asText());
        }

        ObjectMapper mapper = new ObjectMapper();
        String serializedJson = mapper.writerWithView(View.MensagemResumo.class).writeValueAsString(mensagem);

        messagingTemplate.convertAndSendToUser(body.get("id").asText(), "/queue/messages", serializedJson);
        messagingTemplate.convertAndSend("/user/queue/messages", serializedJson);

    }

}
