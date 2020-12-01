package br.gov.sp.fatec.springbootapp.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.springbootapp.controller.View;

@Entity
@Table(name = "usr_usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonView({ View.AtividadeResumo.class, View.UsuarioResumo.class,View.ConversaResumo.class  })
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;

    @JsonView({ View.UsuarioResumo.class, View.AutorizacaoResumo.class, View.MensagemResumo.class,
            View.AtividadeResumo.class,View.ConversaResumo.class })
    @Column(name = "usr_nome")
    private String nome;

    @JsonView(View.UsuarioResumo.class)
    @Column(name = "usr_avatar", columnDefinition = "TEXT")
    private String avatar;

    @Column(name = "usr_senha")
    private String senha;

    @JsonView(View.UsuarioResumo.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "uau_usuario_autorizacao", joinColumns = { @JoinColumn(name = "usr_id") }, inverseJoinColumns = {
            @JoinColumn(name = "aut_id") })
    private Set<Autorizacao> autorizacoes;

    @OneToMany(mappedBy = "atvRemetente", fetch = FetchType.EAGER)
    private Set<Atividade> atividadesEnviadas;

    @OneToMany(mappedBy = "atvDestinatario", fetch = FetchType.EAGER)
    private Set<Atividade> atividadesRecebidas;
    
    @JsonIgnore
    @OneToMany(mappedBy = "remetenteId", fetch = FetchType.LAZY)
    private Set<Mensagem> mensagensEnviadas;

    @JsonIgnore
    @OneToMany(mappedBy = "destinatarioId", fetch = FetchType.LAZY)
    private Set<Mensagem> mensagensRecebidas;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Autorizacao> getAutorizacoes() {
        return autorizacoes;
    }

    public void setAutorizacoes(Set<Autorizacao> autorizacoes) {
        this.autorizacoes = autorizacoes;
    }

    public Set<Atividade> getAtividadesEnviadas() {
        return atividadesEnviadas;
    }

    public void setAtividadesEnviadas(Set<Atividade> atividadesEnviadas) {
        this.atividadesEnviadas = atividadesEnviadas;
    }

    public Set<Atividade> getAtividadesRecebidas() {
        return atividadesRecebidas;
    }

    public void setAtividadesRecebidas(Set<Atividade> atividadesRecebidas) {
        this.atividadesRecebidas = atividadesRecebidas;
    }

    public Set<Mensagem> getMensagensEnviadas() {
        return mensagensEnviadas;
    }

    public void setMensagensEnviadas(Set<Mensagem> mensagensEnviadas) {
        this.mensagensEnviadas = mensagensEnviadas;
    }

    public Set<Mensagem> getMensagensRecebidas() {
        return mensagensRecebidas;
    }

    public void setMensagensRecebidas(Set<Mensagem> mensagensRecebidas) {
        this.mensagensRecebidas = mensagensRecebidas;
    }

}