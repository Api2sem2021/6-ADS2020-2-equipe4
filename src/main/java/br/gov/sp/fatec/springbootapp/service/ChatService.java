package br.gov.sp.fatec.springbootapp.service;

import java.util.Set;

import br.gov.sp.fatec.springbootapp.entity.Conversa;
import br.gov.sp.fatec.springbootapp.entity.Mensagem;

public interface ChatService {

    public Conversa iniciarConversa(Integer status);

    public Conversa buscarConversaPorId(Long id);

    public Set<Conversa> buscarConversaPorStatus(Integer status);

    public Mensagem enviarMensagem(Long chatId, String conteudo, String remetenteNome, String destinatarioNome,
    Long remetenteId, Long destinatarioId,String dataEnvio, String horario);

    public Conversa alterarStatusConversa(Long id,Integer status);

    public Conversa buscarConversaInterna(Long remetenteID, Long destinarioID);
    
}
