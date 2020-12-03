<template>
  <div>
    <div class="row">
      <div class="col s12">
        <div class="card blue-grey darken-1 pop">
          <div class="card-content white-text">
            <span class="card-title" style="padding-block: 5px"
              ><i class="material-icons">history</i> Histórico</span
            >
            <nav>
              <div class="nav-wrapper green lighten-1">
                <div class="input-field">
                  <input
                    placeholder="Buscar por nome do cliente"
                    v-model="nomeBusca"
                    type="search"
                    v-on:change="buscarPorNome()"
                    required
                    buscarPorNome
                  />
                  <label class="label-icon" for="search"
                    ><i class="material-icons">search</i></label
                  >
                  <i class="material-icons">close</i>
                </div>
              </div>
            </nav>
            <div class="scrolable">
              <table class="highlight">
                <thead>
                  <tr>
                    <th>Cliente</th>
                    <th>Atendente</th>
                    <th>Data</th>
                  </tr>
                </thead>

                <tbody>
                  <tr
                    v-for="(conversa, key) in pages[pageActive]"
                    v-bind:key="conversa.id"
                    v-bind:id="key"
                    class="modal-trigger"
                  >
                    <td>{{ conversa.mensagens[0].remetenteNome }}</td>
                    <td>
                      {{
                        conversa.mensagens[conversa.mensagens.length - 1]
                          .remetenteNome
                      }}
                    </td>
                    <td>
                      {{
                        conversa.mensagens[conversa.mensagens.length - 1].data
                      }}
                    </td>
                    <td>
                      <a
                        data-target="modal1"
                        class="btn small grey waves-effect waves-green modal-trigger"
                        v-on:click="
                          () => {
                            this.conversaSelecionada = conversa;
                          }
                        "
                        >Ver histórico</a
                      >
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>

            <ul class="pagination">
              <li
                class="waves-effect"
                v-bind:class="{ disabled: pageActive == 0 }"
              >
                <a v-on:click="this.pageActive -= 1"
                  ><i class="material-icons">chevron_left</i></a
                >
              </li>
              <li
                class="waves-effect"
                v-for="(page, key) in pages"
                v-bind:key="page.id"
                v-bind:id="key"
                v-bind:class="{ active: pageActive === key }"
                v-on:click="this.pageActive = key"
              >
                <a href="#!">{{ key + 1 }}</a>
              </li>
              <li
                class="waves-effect"
                v-bind:class="{ disabled: pageActive == pageCount }"
              >
                <a v-on:click="this.pageActive += 1"
                  ><i class="material-icons">chevron_right</i></a
                >
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div id="modal1" class="modal modal-fixed-footer blue-grey darken-1">
      <div class="modal-content" style="height: auto">
        <div class="row" v-if="conversaSelecionada != null">
          <h4 class="center-align green-text">Conversa</h4>
          <div class="divider"></div>
          <blockquote class="white-text">
            <div class="col s6">
              <strong>Atendente:</strong>
              {{
                conversaSelecionada.mensagens[
                  conversaSelecionada.mensagens.length - 1
                ].remetenteNome
              }}
            </div>
            <div>
              <strong style="margin-left: 100px">Cliente:</strong>
              {{ conversaSelecionada.mensagens[0].remetenteNome }}
            </div>
          </blockquote>
          <div
            class="green lighten-3 chat-wrapper2 col s12"
            ref="chat"
            style="padding-bottom: 15px"
          >
            <div
              v-for="(mensagem, key) in conversaSelecionada.mensagens"
              v-bind:key="mensagem.id"
              v-bind:id="key"
            >
              <div class="col l12">
                <div
                  class="col s8 m4 l4 white z-depth-3 message pop"
                  v-if="mensagem.destinatarioNome == null"
                  style="
                    padding-left: 10px !important;
                    padding-bottom: 0px !important;
                    padding-top: 0px !important;
                  "
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
                class="col s8 m4 l4 offset-s4 offset-m8 offset-l8 white z-depth-3 message pop"
                v-if="mensagem.destinatarioNome != null"
                style="
                  padding-left: 10px !important;
                  padding-bottom: 0px !important;
                  padding-top: 0px !important;
                "
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
        </div>
      </div>

      <div class="modal-footer blue-grey darken-1">
        <a class="modal-close waves-effect waves-green btn red">
          <i class="material-icons left">close</i> Fechar</a
        >
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import M from "materialize-css";

import utils from "../utils";

export default {
  name: "History",
  data() {
    return {
      conversaSelecionada: null,
      pages: [],
      pageCount: 0,
      pageActive: 0,
      nomeBusca: "",
    };
  },
  methods: {
    async buscarConversas() {
      await axios
        .get(
          `${this.$store.state.apiUrl}/chat/buscarConversasPorStatus?status=1`
        )
        .then((conversas) => {
          conversas = conversas.data;
          this.conversas = conversas;
          this.pagination(conversas);
        });
    },
    pagination(conversas) {
      this.pages = [];
      this.pageCount = 0;
      this.pageActive = 0;

      conversas.forEach((conversa) => {
        if (conversa.mensagens.length >= 1) {
          conversa.mensagens[
            conversa.mensagens.length - 1
          ].data = utils.dateFormat(conversa.mensagens[0].data);
          if (this.pages.length === 0) {
            this.pages[this.pageCount] = [];
            this.pages[this.pageCount].push(conversa);
          } else if (this.pages[this.pageCount].length < 6) {
            this.pages[this.pageCount].push(conversa);
          } else {
            this.pageCount += 1;
            this.pages[this.pageCount] = [];
            this.pages[this.pageCount].push(conversa);
          }
        }
      });
    },
    buscarPorNome() {
      let conversasFiltradosPorNome = [];
      this.conversas.forEach((conversa) => {
        if (
          conversa.mensagens[0].remetenteNome
            .toLowerCase()
            .includes(this.nomeBusca.toLowerCase())
        ) {
          conversasFiltradosPorNome.push(conversa);
        }
      });
      this.pagination(conversasFiltradosPorNome);
      return;
    },
  },
  beforeMount() {
    this.buscarConversas();
  },
  mounted() {
    M.Modal.init(document.querySelectorAll(".modal"));
  },
};
</script>

<style>
.chat-wrapper2 {
  overflow-y: hidden;
  padding-left: 15px;
  padding-right: 15px;
  padding-bottom: 15px;
  height: 260px;
}

.chat-wrapper2:hover {
  overflow-y: auto;
}
</style>
