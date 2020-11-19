<template>
  <div class="row">
    <div class="col s12 m12">
      <div class="card blue-grey darken-1" style="">
        <div class="card-content">
          <div class="row" style="height: 500px">
            <div
              class="blue-grey lighten-1 col s12 m4 l4"
              style="height: 500px"
            >
              <div
                class="green lighten-1 white-text z-depth-2"
                style="margin: 10px"
              >
                <div style="padding: 10px">
                  <span class="card-title center-align"
                    ><i class="material-icons">arrow_downward</i>Conversas ao
                    vivo</span
                  >
                  <p class="center-align">
                    As conversas listadas abaixo estão acontecendo em tempo real
                  </p>
                </div>
              </div>
              <ul class="collection z-depth-2">
                <li
                  class="collection-item avatar pop li-active"
                  v-for="(conversa, key) in conversas"
                  v-bind:key="conversa.id"
                  v-bind:id="key"
                >
                  <i class="large material-icons circle blue z-depth-2"
                    >person</i
                  >
                  <strong
                    class="title green-text lighten-1"
                    v-on:click="() => this.changeConversa(conversa)"
                    >{{ conversa.mensagens[0].remetenteNome }}</strong
                  >
                  <p class="truncate">
                    {{
                      conversa.mensagens[conversa.mensagens.length - 1].conteudo
                    }}
                  </p>
                  <span
                    class="secondary-content green-text lighten-1"
                    style="font-weight: bolder"
                    >{{
                      conversa.mensagens[
                        conversa.mensagens.length - 1
                      ].hora.slice(0, 5)
                    }}</span
                  >
                </li>
              </ul>
            </div>

            <nav class="col s12 m8 l8 green lighten-1">
              <div class="nav-wrapper">
                <a href="#!" class="brand-logo"
                  ><i
                    src="https://www.vagalume.com.br/pepe-moreno/images/pepe-moreno.jpg"
                    class="material-icons"
                    >person</i
                  >{{ conversaNome }}</a
                >
                <ul class="right hide-on-med-and-down">
                  <li>
                    <a><i class="material-icons">more_vert</i></a>
                  </li>
                </ul>
              </div>
            </nav>
            <div class="col s12 m8 l8" style="padding: 0px !important">
              <div class="green lighten-3 chat-wrapper">
                <div
                  v-for="(mensagem, key) in conversaSelecionada"
                  v-bind:key="mensagem.id"
                  v-bind:id="key"
                >
                  <div class="col l12">
                    <div
                      class="col s8 m4 l4 white z-depth-3 message pop"
                      v-if="mensagem.destinatarioNome == null"
                    >
                      <p class="left-align">
                        {{ mensagem.conteudo }}
                      </p>

                      <p class="right-align green-text lighten-1">
                        {{ mensagem.hora.slice(0, 5) }}
                      </p>
                    </div>
                  </div>
                </div>

                <div
                  class="col s8 m4 l4 offset-s4 offset-m8 offset-l8 white z-depth-3 message pop"
                  v-if="mensagem.destinatarioNome != null"
                >
                  <p class="left-align">
                    {{ mensagem.conteudo }}
                  </p>
                  <p class="right-align green-text lighten-1">
                    {{ mensagem.hora.slice(0, 5) }}
                  </p>
                </div>
              </div>
              <div
                class="blue-grey lighten-1 col s12 z-depth-5"
                style="height: 68.5px"
              >
                <div class="input-field col s8 search-fiel white-text">
                  <i class="material-icons prefix">edit</i>
                  <input
                    v-model="mensagem"
                    type="text"
                    class="validate white-text"
                  />
                  <label for="search" class="white-text"
                    >Digite a sua mensagem</label
                  >
                </div>
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptas, odit?
                <a
                  class="col s4 btn-large waves-effect green lighten-1"
                  style="margin-top: 5px"
                  v-on:click="sendName()"
                  ><i class="material-icons left">send</i>Enviar</a
                >
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
var stompClient;
var socket;
export default {
  name: "Chat",
  data() {
    return {
      mensagem: "",
      url: "http://localhost:8081/spring-app/",
      mensagens: [],
      listaConversas: [],
      conversas: [],
      conversaSelecionada: [],
      conversaSelecionadaID: "",
      conversaNome: "",
    };
  },
  methods: {
    changeConversa(conversa) {
      this.conversaSelecionada = conversa.mensagens;
      this.conversaSelecionadaID = conversa.id;
      this.conversaNome = conversa.mensagens[0].remetenteNome;
    },
    connect() {
      socket = new SockJS(`${this.url}ws`);
      stompClient = Stomp.over(socket);
      stompClient.connect(
        { Authorization: this.$store.state.token },
        (frame) => {
          console.log("Connected: " + frame);
          stompClient.subscribe("/topic/greetings", (greeting) => {
            console.log(greeting);
            greeting = JSON.parse(greeting.body);
            this.showGreeting(greeting);
          });
        }
      );
    },
    disconnect() {
      if (stompClient !== null) {
        stompClient.disconnect();
      }
      this.setConnected(false);
      console.log("Disconnected");
    },
    
    sendName() {
      
      let data = new Date();
      let tete  = JSON.stringify({
          "conteudo": this.mensagem,
          "id": this.conversaSelecionadaID,
          "data": "00:00 - 22/02/2021",
          "hora": `${data.getHours()}:${data.getMinutes()}:${data.getSeconds()}`,
          "destinatarioNome": "admin"
        })
      stompClient.send(
        "/app/hello",
        {},
        
      );
      stompClient.send("/app/hello", {},`{"conteudo": ${this.mensagem},"id": ${this.conversaSelecionadaID},"data": "00:00 - 22/02/2021","hora": ${data.getHours()}:${data.getMinutes()}:${data.getSeconds()},"destinatarioNome": "admin"}`);
      this.mensagem = "";
    },

    async showGreeting(mensagem) {
      console.log(mensagem);
      if (this.listaConversas.indexOf(mensagem.conversa.id) == -1) {
        this.listaConversas.push(mensagem.conversa.id);
        await axios
          .get(
            `http://localhost:8081/spring-app/chat/buscarPorId?id=${mensagem.conversa.id}`
          )
          .then((conversa) => {
            console.log(conversa.data);
            conversa.data.id = mensagem.conversa.id;
            this.conversas = [...this.conversas, conversa.data];
            console.log(this.conversas);
          });
      } else {
        this.buscaConversas();
      }
      this.changeConversa()
    },
    async buscaConversas() {
      this.listaConversas = [];
      this.conversas = [];
      await axios
        .get("http://localhost:8081/spring-app/chat/buscarMensagemAtivas")
        .then((mensagensAtivas) => {
          mensagensAtivas.data.forEach(async (mensagem) => {
            if (this.listaConversas.indexOf(mensagem.conversa.id) == -1) {
              this.listaConversas.push(mensagem.conversa.id);
              await axios
                .get(
                  `http://localhost:8081/spring-app/chat/buscarPorId?id=${mensagem.conversa.id}`
                )
                .then((conversa) => {
                  console.log(conversa.data);
                  conversa.data.id = mensagem.conversa.id;
                  this.conversas = [...this.conversas, conversa.data];
                  console.log(this.conversas);
                });
            }
          });
        });
    },
  },
  async beforeMount() {
    await axios
      .get("http://localhost:8081/spring-app/chat/buscarMensagemAtivas")
      .then((mensagensAtivas) => {
        mensagensAtivas.data.forEach(async (mensagem) => {
          if (this.listaConversas.indexOf(mensagem.conversa.id) == -1) {
            this.listaConversas.push(mensagem.conversa.id);
            await axios
              .get(
                `http://localhost:8081/spring-app/chat/buscarPorId?id=${mensagem.conversa.id}`
              )
              .then((conversa) => {
                console.log(conversa.data);
                conversa.data.id = mensagem.conversa.id;
                this.conversas = [...this.conversas, conversa.data];
                console.log(this.conversas);
              });
          } else {
            // this.conversas[`${mensagem.conversa.id}`].mensagens = [...this.conversas[`${mensagem.conversa.id}`].mensagens, mensagem]
          }
        });
      });
    this.connect();
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

.li-active {
  border-right: 5px solid #66bb6a;
  background-color: #ddd !important;
}
</style>
