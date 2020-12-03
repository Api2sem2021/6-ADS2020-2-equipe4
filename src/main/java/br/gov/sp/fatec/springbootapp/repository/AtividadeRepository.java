package br.gov.sp.fatec.springbootapp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.sp.fatec.springbootapp.entity.Atividade;
import br.gov.sp.fatec.springbootapp.entity.Usuario;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
        // List<Notificacao> findByUsuario(Usuario Usuario);

        // @Query("select n from Notificacao n where n.titulo = ?1 and n.send_by = ?2")
        // List<Notificacao> findByTituloERemetente(String titulo, Long idRemetente);

        // //Consulta com join
        // @Query("select n from Notificacao n inner join n.usuario u where u.id = ?1")
        // public List<Notificacao> buscaPorNomeUsuario(Long idUsuario);

        @Query("delete from Atividade a where a.id=?1")
        public void deleteAtividadePorId(Long atvID);

        @Query("select a from Atividade a where a.status = ?1 AND a.atvDestinatario = ?2")
        public Set<Atividade> buscarAbertas(Integer status, Usuario usuario);
}