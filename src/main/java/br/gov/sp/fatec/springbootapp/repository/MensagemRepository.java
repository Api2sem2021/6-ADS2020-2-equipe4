package br.gov.sp.fatec.springbootapp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.sp.fatec.springbootapp.entity.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    
@Query("select m from Mensagem m where m.remetenteNome != null or m.destinatarioNome != null order by m.hora DESC")
public Set<Mensagem> buscarMensagensAtivas();

}
