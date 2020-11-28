package br.gov.sp.fatec.springbootapp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.sp.fatec.springbootapp.entity.Conversa;

public interface ConversaRepository extends JpaRepository<Conversa, Long> {
    
    @Query("delete from Conversa c where c.id=?1")
    public void deletarConversaPorID(Long id);

    @Query("select c from Conversa c where c.id=?1 ")
    public Conversa buscarPorId(Long id);

    @Query("select c from Conversa c where c.status=?1 order by c.id DESC")
    public Set<Conversa> buscarPorStatus(Integer status);
}