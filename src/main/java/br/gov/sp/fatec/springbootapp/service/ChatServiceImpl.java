package br.gov.sp.fatec.springbootapp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.springbootapp.entity.Conversa;
import br.gov.sp.fatec.springbootapp.entity.Mensagem;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
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
    public Conversa iniciarConversa(Integer status) {
        Conversa conversa = new Conversa();

        conversa.setStatus(status);
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
            Long remetenteId, Long destinatarioId, String dataEnvio, String horario) {
        Conversa conversa = conversaRepo.buscarPorId(chatId);

        if (conversa != null) {
            Date data;
            Date hora;
            try {
                data = formatoData.parse(dataEnvio);
                hora = formatoHora.parse(horario);

                Mensagem mensagem = new Mensagem();
                mensagem.setConteudo(conteudo);
                mensagem.setData(data);
                mensagem.setHora(hora);
                mensagem.setRemetenteNome(remetenteNome);
                mensagem.setDestinatarioNome(destinatarioNome);
                if(remetenteId != null){
                    Optional<Usuario> remetenteOp = usuarioRepo.findById(remetenteId);
                    Usuario remetente = remetenteOp.get();
                    mensagem.setRemetenteId(remetente);
                }
                if(destinatarioId != null){
                    Optional<Usuario> destinatarioOp = usuarioRepo.findById(destinatarioId);
                    Usuario destinatario = destinatarioOp.get();
                    mensagem.setDestinatarioId(destinatario);
                }
                mensagem.setConversa(conversa);
                mensagemRepo.save(mensagem);
                return mensagem;
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public Set<Conversa> buscarConversaPorStatus(Integer status) {
        Set<Conversa> conversas = conversaRepo.buscarPorStatus(status);
        if(conversas != null){
            return conversas;
        }

        throw new RegistroNaoEncontradoException("Nenhuma conversa encontrada para o status fornecido");
        
    }

    @Override
    public Conversa alterarStatusConversa(Long id, Integer status) {
        Conversa conversa = conversaRepo.buscarPorId(id);

        if(conversa != null){
            conversa.setStatus(status);
            conversaRepo.save(conversa);
            return conversa;
        }
        throw new RegistroNaoEncontradoException("Nenhuma conversa encontrada para o id:" + id);
    }

    @Override
    public Conversa buscarConversaInterna(Long remetenteID, Long destinarioID) {
        Optional<Usuario> remetenteOp = usuarioRepo.findById(remetenteID);
        Optional<Usuario> destinarioOp = usuarioRepo.findById(destinarioID);

        Usuario remetente = remetenteOp.get();
        Usuario destinatario = destinarioOp.get();

        Conversa conversa = conversaRepo.buscarConversaInterna(remetente, destinatario);
        return conversa;
    }
    
}
