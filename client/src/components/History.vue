<template>
  <div class="row">
      <div class="col s12">
        <div class="card blue-grey darken-1 pop">
          <div class="card-content white-text">
            <span class="card-title" style="padding-block: 5px"><i class="material-icons">history</i> Histórico</span>
            <nav>
              <div class="nav-wrapper green lighten-1">
                  <div class="input-field">
                    <input placeholder="Buscar por nome do cliente"  v-model="nomeBusca" type="search" v-on:change="buscarPorNome()" required buscarPorNome/>
                    <label class="label-icon" for="search"><i class="material-icons">search</i></label>
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
                    <th>Data da ultima mensagem</th>
                  </tr>
                </thead>

                <tbody>
                  <tr v-for="(conversa, key) in pages[pageActive]" v-bind:key="conversa.id" v-bind:id="key">
                    <td>{{conversa.mensagens[0].remetenteNome}}</td>
                    <td>{{conversa.mensagens[conversa.mensagens.length - 1].remetenteNome}}</td>
                    <td>{{conversa.mensagens[conversa.mensagens.length - 1].data}}</td>
                  </tr>
                </tbody>
              </table>
            </div>

            <ul class="pagination">
              <li class="waves-effect" v-bind:class="{ disabled: pageActive == 0}">
                <a v-on:click="this.pageActive -= 1"><i class="material-icons">chevron_left</i></a>
              </li>
              <li class="waves-effect" 
                  v-for="(page, key) in pages" 
                  v-bind:key="page.id" 
                  v-bind:id="key"
                  v-bind:class="{ active: pageActive === key }"
                  v-on:click="this.pageActive = key">
                  <a href="#!">{{key + 1}}</a>
              </li>
              <li class="waves-effect" v-bind:class="{ disabled: pageActive == pageCount}">
                <a v-on:click="this.pageActive += 1"><i class="material-icons">chevron_right</i></a>
              </li>
            </ul>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import axios from "axios";

import utils from "../utils";

export default {
  name: "History",
  data() {
    return {
      conversas: [],
      pages: [],
      pageCount: 0,
      pageActive: 0,
      nomeBusca: ""
    };
  },
  methods: {
    async buscarConversas() {
      await axios.get(`${this.$store.state.apiUrl}/chat/buscarConversasPorStatus?status=1`).then((conversas) => {
        conversas = conversas.data;
        this.conversas = conversas;
        this.pagination(conversas)
      });
    },
    pagination(conversas){
      this.pages = [];
      this.pageCount = 0;
      this.pageActive = 0;

      conversas.forEach((conversa) => {
          if (conversa.mensagens.length >= 1) {
              console.log(conversa.mensagens[conversa.mensagens.length - 1].data)
            conversa.mensagens[0].data = utils.dateFormat(conversa.mensagens[0].data);
            conversa.mensagens[0].hora = utils.hourFormat(conversa.mensagens[0].hora);
            if(this.pages.length === 0 ){
                this.pages[this.pageCount] = [];
                this.pages[this.pageCount].push(conversa);
            }
            else if(this.pages[this.pageCount].length < 6){
                this.pages[this.pageCount].push(conversa);
            }
            else{
                this.pageCount += 1;
                this.pages[this.pageCount] = [];
                this.pages[this.pageCount].push(conversa);
            }
          }
        });
    },
    buscarPorNome() {
      let conversasFiltradosPorNome = [];
      this.conversas.forEach(conversa =>{
        if(conversa.mensagens[0].remetenteNome.toLowerCase().includes(this.nomeBusca.toLowerCase())){
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
};
</script>
