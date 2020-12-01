package br.gov.sp.fatec.springbootapp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.sp.fatec.springbootapp.entity.Conversa;
import br.gov.sp.fatec.springbootapp.entity.Usuario;

public interface ConversaRepository extends JpaRepository<Conversa, Long> {
    
    @Query("delete from Conversa c where c.id=?1")
    public void deletarConversaPorID(Long id);

    @Query("select c from Conversa c where c.id=?1 ")
    public Conversa buscarPorId(Long id);

    @Query("select c from Conversa c join c.mensagens m where m.remetenteId=?1 AND m.destinatarioId=?2 OR m.remetenteId=?2 AND m.destinatarioId=?1")
    public Conversa buscarConversaInterna(Usuario remetente, Usuario destinatario);

    @Query("select c from Conversa c where c.status=?1 order by c.id DESC")
    public Set<Conversa> buscarPorStatus(Integer status);
}