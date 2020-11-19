package br.gov.sp.fatec.springbootapp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.springbootapp.controller.View;

@Entity
@Table(name = "mensagens")
public class Mensagem {

    @JsonView(View.ConversaResumo.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_id")
    private Long id;

    @JsonView({View.MensagemResumo.class, View.ConversaResumo.class})
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;

    @JsonView({View.MensagemResumo.class, View.ConversaResumo.class})
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;

    @JsonView({View.MensagemResumo.class, View.ConversaResumo.class})
    @Column(name = "conteudo")
    private String conteudo;

    @JsonView({View.MensagemResumo.class, View.ConversaResumo.class})
    @Column(name = "remetente_nome")
    private String remetenteNome;

    @JsonView({View.MensagemResumo.class, View.ConversaResumo.class})
    @Column(name = "destinatario_nome")
    private String destinatarioNome;

    // @JsonView(View.MensagemResumo.class)
    @ManyToOne
    @JoinColumn(name = "remetente_id")
    private Usuario remetenteId;

    // @JsonView(View.MensagemResumo.class)
    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Usuario destinatarioId;

    @JsonView(View.MensagemResumo.class)
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Conversa conversa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getRemetenteNome() {
        return remetenteNome;
    }

    public void setRemetenteNome(String remetenteNome) {
        this.remetenteNome = remetenteNome;
    }

    public String getDestinatarioNome() {
        return destinatarioNome;
    }

    public void setDestinatarioNome(String destinatarioNome) {
        this.destinatarioNome = destinatarioNome;
    }

    public Usuario getRemetenteId() {
        return remetenteId;
    }

    public void setRemetenteId(Usuario remetenteId) {
        this.remetenteId = remetenteId;
    }

    public Usuario getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(Usuario destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Conversa getConversa() {
        return conversa;
    }

    public void setConversa(Conversa conversa) {
        this.conversa = conversa;
    }


}
