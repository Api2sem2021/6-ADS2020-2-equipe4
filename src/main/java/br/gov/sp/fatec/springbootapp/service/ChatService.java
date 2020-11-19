package br.gov.sp.fatec.springbootapp.service;

import br.gov.sp.fatec.springbootapp.entity.Conversa;
import br.gov.sp.fatec.springbootapp.entity.Mensagem;

public interface ChatService {

    public Conversa iniciarConversa();

    public Conversa buscarConversaPorId(Long id);

    public Mensagem enviarMensagem(Long chatId, String conteudo, String remetenteNome, String destinatarioNome,
    Long remetenteId, Long destinatarioId);
    
}
