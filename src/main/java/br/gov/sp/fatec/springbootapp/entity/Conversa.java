package br.gov.sp.fatec.springbootapp.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.springbootapp.controller.View;

@Entity
@Table(name = "conversas")
public class Conversa {

    @JsonView({View.MensagemResumo.class, View.ConversaResumo.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @Column(name = "assumida")
    private Integer assumida;

    @Column(name = "status")
    private Integer status;
    
    @JsonView(View.ConversaResumo.class)
    @OneToMany(mappedBy = "conversa", fetch = FetchType.LAZY)
    private Set<Mensagem> mensagens;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAssumida() {
        return assumida;
    }

    public void setAssumida(Integer assumida) {
        this.assumida = assumida;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(Set<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    
}