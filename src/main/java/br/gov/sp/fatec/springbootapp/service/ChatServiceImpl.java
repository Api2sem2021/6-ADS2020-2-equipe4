package br.gov.sp.fatec.springbootapp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.springbootapp.controller.View;
import br.gov.sp.fatec.springbootapp.entity.Conversa;
import br.gov.sp.fatec.springbootapp.entity.Mensagem;
import br.gov.sp.fatec.springbootapp.exception.RegistroNaoEncontradoException;
import br.gov.sp.fatec.springbootapp.repository.ConversaRepository;
import br.gov.sp.fatec.springbootapp.repository.MensagemRepository;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;

@Service("chatService")
public class ChatServiceImpl implements ChatService {

    @Autowired
    UsuarioRepository usuarioRepo;

    @Autowired
    ConversaRepository conversaRepo;

    @Autowired
    MensagemRepository mensagemRepo;

    SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

    @Override
    public Conversa iniciarConversa() {
        Conversa conversa = new Conversa();

        conversa.setStatus(0);
        conversa.setAssumida(0);

        conversaRepo.save(conversa);
        return conversa;

    }

    @Override
    public Conversa buscarConversaPorId(Long id) throws RegistroNaoEncontradoException {
        Conversa conversa = conversaRepo.buscarPorId(id);

        if (conversa != null) {
            return conversa;
        }
        throw new RegistroNaoEncontradoException("Conversa não encontrada para o id fornecido");
    }

    @Override
    @Transactional
    public Mensagem enviarMensagem(Long chatId, String conteudo, String remetenteNome, String destinatarioNome,
    Long remetenteId, Long destinatarioId, String horario) {
        Conversa conversa = conversaRepo.buscarPorId(chatId);

        if (conversa != null) {
            Date data;
            Date hora;
            try {
                data = formatoData.parse("2017-11-15");
                hora = formatoHora.parse(horario);
                
                Mensagem mensagem = new Mensagem();
                mensagem.setConteudo(conteudo);
                mensagem.setData(data);
                mensagem.setHora(hora);
                mensagem.setRemetenteNome(remetenteNome);
                mensagem.setRemetenteId(null);
                mensagem.setConversa(conversa);
                mensagemRepo.save(mensagem);
                return mensagem;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            
        }
		return null;
    }
    
}
