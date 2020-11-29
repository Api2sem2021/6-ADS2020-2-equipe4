<template>
  <div class="row">
    <div class="col s12 m12">
      <div class="card blue-grey darken-1" style="">
        <div class="card-content">
          <div class="row" style="height: 500px">
            <div class="blue-grey lighten-1 col s12 m4 l4" style="height: 500px">
              <nav>
                <div class="nav-wrapper green lighten-1" style="margin-top: 5px">
                  <form>
                    <div class="input-field">
                      <input id="search" type="search" required placeholder="Buscar por nome"/>
                      <label class="label-icon" for="search"><i class="material-icons">search</i></label>
                      <i class="material-icons">close</i>
                    </div>
                  </form>
                </div>
              </nav>
              <ul class="collection z-depth-2">
                <li class="collection-item avatar pop" v-for="(conversa, key) in conversas" v-bind:key="conversa.id" v-bind:id="key" v-on:click="() => this.changeConversa(conversa, key)" v-bind:class="{ liAactive: this.conversaSelecionadaID === conversa.id }">
                  <i class="large material-icons circle blue z-depth-2">person</i>
                  <strong class="title green-text lighten-1">{{ conversa.mensagens[0].remetenteNome }}</strong>
                  <p class="truncate">
                    {{ conversa.mensagens[conversa.mensagens.length - 1].conteudo }}
                  </p>
                  <span class="secondary-content green-text lighten-1" style="font-weight: bolder">{{ conversa.mensagens[conversa.mensagens.length - 1].hora.slice(0, 5) }}</span>
                </li>
              </ul>
            </div>

            <nav class="col s12 m8 l8 green lighten-1">
              <div class="nav-wrapper">
                <a href="#!" class="brand-logo"><i class="material-icons">person</i>{{ conversaNome }}</a>
              </div>
            </nav>
            <div class="col s12 m8 l8" style="padding: 0px !important">
              <div class="green lighten-3 chat-wrapper" ref="chat">
                <div v-for="(mensagem, key) in conversaSelecionada" v-bind:key="mensagem.id" v-bind:id="key">
                  <div class="col l12">
                    <div class="col s8 m4 l4 white z-depth-3 message pop" v-if="mensagem.destinatarioNome == null">
                      <p class="left-align">
                        {{ mensagem.conteudo }}
                      </p>
                      <p class="right-align green-text lighten-1">
                        {{ mensagem.hora.slice(0, 5) }}
                      </p>
                    </div>
                  </div>

                  <div class="col s8 m4 l4 offset-s4 offset-m8 offset-l8 white z-depth-3 message pop" v-if="mensagem.destinatarioNome != null">
                    <p class="left-align">
                      {{ mensagem.conteudo }}
                    </p>
                    <p class="right-align green-text lighten-1">
                      {{ mensagem.hora.slice(0, 5) }}
                    </p>
                  </div>
                </div>
              </div>
              <div class="blue-grey lighten-1 col s12 z-depth-5" style="height: 68.5px">
                <div class="input-field col s8 search-fiel white-text">
                  <i class="material-icons prefix">edit</i>
                  <input v-model="mensagem" type="text" class="validate white-text" />
                  <label for="search" class="white-text">Digite a sua mensagem</label>
                </div>
                <a class="col s4 btn-large waves-effect green lighten-1" style="margin-top: 5px" v-on:click="sendMessage()"><i class="material-icons left">send</i>Enviar</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import axios from "axios";
import M from "materialize-css";

import utils from "../utils";

var stompClient;
var socket;
export default {
  name: "InternalChat",
  data() {
    return {
      conversasID: [],
      mensagem: "",
      conversas: [],
      conversaSelecionada: [],
      conversaSelecionadaID: null,
      conversaSelecionadaKey: null,
      conversaNome: "",
    };
  },
  methods: {
    changeConversa(conversa, key) {
      this.conversaSelecionada = conversa.mensagens;
      this.conversaSelecionadaID = conversa.id;
      this.conversaNome = conversa.mensagens[0].remetenteNome;
      this.conversaSelecionadaKey = key;
    },
    clearConversa() {
      this.conversaSelecionada = null;
      this.conversaSelecionadaID = null;
      this.conversaNome = null;
      this.conversaSelecionadaKey = null;
    },
    connect() {
      socket = new SockJS(`${this.$store.state.apiUrl}/ws`);
      stompClient = Stomp.over(socket);
      stompClient.connect({ Authorization: this.$store.state.token }, (frame) => {
        console.log("Connected: " + frame);
        stompClient.subscribe("/user/queue/messages", (msg) => {
          msg = JSON.parse(msg.body);
          this.handleMessage(msg);
        });
      });
    },
    disconnect() {
      if (stompClient !== null) {
        stompClient.disconnect();
      }
      this.setConnected(false);
      console.log("Disconnected");
    },

    sendMessage() {
      let body = JSON.stringify({
        conteudo: this.mensagem,
        id: this.conversaSelecionadaID,
        data: utils.dateFormat(),
        hora: utils.hourFormat(),
        destinatarioNome: this.conversaNome,
        nomeRemetente: this.$store.state.usuario.nome,
        destinatarioID: null,
        remetenteID: null,
        origem: "painel",
      });

      stompClient.send("/app/messageHandler", body);
      this.mensagem = "";
    },

    async handleMessage(msg) {
      if (this.conversasID.indexOf(msg.conversa.id) != -1) {
        if (typeof msg.hora != "string") {
          msg.hora = utils.hourFormat(msg.hora);
          msg.data = utils.dateFormat(msg.data);
        }
        this.conversas[this.conversasID.indexOf(msg.conversa.id)].mensagens.push(msg);
      } else {
        await axios.get(`${this.$store.state.apiUrl}/chat/buscarPorId?id=${msg.conversa.id}`).then((conversa) => {
          conversa = conversa.data;
          this.conversas.push(conversa);
          this.conversasID.push(conversa.id);
        });
      }
      setTimeout(() => {
        this.$refs.chat.scrollTop = this.$refs.chat.scrollHeight;
      }, 500);
      return;
    },
    async buscarConversas() {
      await axios.get(`${this.$store.state.apiUrl}/chat/buscarConversasPorStatus?status=0`).then((conversasAtivas) => {
        if (conversasAtivas.data.length >= 1) {
          conversasAtivas = conversasAtivas.data;
          conversasAtivas.forEach((conversa) => {
            if (conversa.mensagens.length >= 1) {
              this.conversas.push(conversa);
              this.conversasID.push(conversa.id);
            }
          });
        }
      });
    },
    async finalizarConversa(id, key) {
      this.mensagem = "Conversa finalizada";
      this.sendMessage();
      await axios.post(`${this.$store.state.apiUrl}/chat/alterarStatus`, { id, status: 1 }).then(() => {
        document.getElementById(key).classList.add("popOut");
        M.toast({ html: "Conversa finalizada" });
        setTimeout(() => {
          this.conversas.splice(key, 1);
        }, 500);
        setTimeout(() => {
          this.clearConversa();
        }, 500);
      });
      return;
    },
  },
  async beforeMount() {
    this.buscarConversas();
    this.connect();
  },
  mounted() {
    M.Dropdown.init(this.$refs.dropdown);
  },
};
</script>

<style>
.avatar-message {
  margin-top: 5px !important;
}
.message {
  width: 270px;
  margin-top: 5px;
  padding: 15px !important;
}

.chat-wrapper {
  overflow-y: hidden;
  padding-left: 15px;
  padding-right: 15px;
  padding-bottom: 15px;
  height: 370px;
}

.chat-wrapper:hover {
  overflow-y: auto;
}
.hoverableli :hover {
  background-color: #ddd;
}
strong {
  font-weight: bolder;
}

.rounded {
  border-radius: 25px;
}

.collection {
  height: 350px !important;
  border: none;
}
.collection:hover {
  overflow-y: auto;
}

.collection-item:hover {
  border-right: 5px solid #66bb6a;
  background-color: #ddd !important;
}
.liAactive {
  border-right: 5px solid #66bb6a;
  background-color: #ddd !important;
}
</style>
