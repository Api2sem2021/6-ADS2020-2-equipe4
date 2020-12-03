<template>
  <div>
    <header class="blue-grey darken-1" style="padding-left: 300px">
      <ul
        id="mobile-nav"
        class="sidenav sidenav-fixed lateral-sidenav blue-grey darken-1"
      >
        <li>
          <div class="user-view">
            <div class="background green lighten-1"></div>
            <a href="#user"
              ><img class="circle" src="../assets/profile.png"
            /></a>
            <a href="#name"
              ><span class="white-text name">{{ usuario.nome }}</span></a
            >
            <a v-if="$store.getters.getUsuario.autorizacoes[0].nome == 'ROLE_ADMIN'" href="#email"><span class="white-text email">Diretor</span></a>
            <a v-else href="#email"><span class="white-text email">Atendente</span></a>
          </div>
        </li>
        <li
          style="cursor: pointer"
          v-on:click="
            $store.commit('setSection', 'activities');
            navigate('/panel/activities');
          "
          class="white-text"
          v-bind:class="{
            menuActive: $store.getters.getSection == 'activities',
          }"
        >
          <a class="waves-effect waves-light white-text">
            <i class="material-icons white-text">notifications</i
            >Atividades<span class="new badge blue" data-badge-caption="abertas"
              >{{$store.getters.getAtvAbertas}}</span
            ></a
          >
        </li>
        <li
          style="cursor: pointer"
          v-on:click="
            $store.commit('setSection', 'internalChat');
            navigate('/panel/internalChat');
          "
          v-bind:class="{
            menuActive: $store.getters.getSection == 'internalChat',
          }"
        >
          <a class="white-text">
            <i class="material-icons white-text">chats</i>Conversas Internas</a
          >
        </li>
        <li
          style="cursor: pointer"
          v-on:click="
            $store.commit('setSection', 'live_chats');
            navigate('/panel/live_chats');
          "
          v-bind:class="{
            menuActive: $store.getters.getSection == 'live_chats',
          }"
        >
          <a class="white-text">
            <i class="material-icons white-text">record_voice_over</i>Conversas
            widget</a
          >
        </li>
        <li
          style="cursor: pointer"
          v-on:click="
            $store.commit('setSection', 'history');
            navigate('/panel/history');
          "
          v-bind:class="{ menuActive: $store.getters.getSection == 'history' }"
        >
          <a class="white-text">
            <i class="material-icons white-text">history</i>Histórico</a
          >
        </li>
        <li
          style="cursor: pointer"
          v-on:click="
            $store.commit('setSection', 'employes');
            navigate('/panel/employes');
          "
          v-bind:class="{ menuActive: $store.getters.getSection == 'employes' }"
        >
          <a class="white-text">
            <i class="material-icons white-text">group</i>Funcionarios</a
          >
        </li>
      </ul>

      <ul
        id="notifications-side-nav"
        class="sidenav notifications-sidenav"
        style="width: 250px"
      >
        <ul class="collection with-header">
          <li class="collection-header">
            <h4>Notificações</h4>
          </li>
        </ul>
      </ul>
    </header>

    <div class="teste">
      <nav>
        <div class="nav-wrapper blue-grey darken-1">
          <a href="#" class="brand-logo center">
            <img style="width: 100%" src="../assets/logo.png"
          /></a>
          <ul class="left hide-on-large">
            <li>
              <a href="#" data-target="mobile-nav" class="sidenav-trigger"
                ><i class="material-icons">menu</i></a
              >
            </li>
          </ul>
          <ul class="right hide-on-med-and-down">
            <li>
              <a
                data-target="notifications-side-nav"
                class="sidenav-trigger"
                style="display: block"
                ><i class="material-icons">notifications</i></a
              >
            </li>
            <li>
              <a v-on:click="logout()"
                ><i class="material-icons">exit_to_app</i></a
              >
            </li>
          </ul>
        </div>
      </nav>

      <router-view></router-view>
    </div>

    <div class="fixed-action-btn hide-on-large-only pop">
      <a class="btn-floating btn-large green lighten-1">
        <i class="large material-icons">settings</i>
      </a>
      <ul>
        <li>
          <a
            class="btn-floating yellow darken-1 sidenav-trigger"
            data-target="notifications-side-nav"
            ><i class="material-icons">notifications</i></a
          >
        </li>
        <li>
          <a class="btn-floating red" v-on:click="logout()"
            ><i class="material-icons">exit_to_app</i></a
          >
        </li>
      </ul>
    </div>
  </div>
</template>

'

<script>
import axios from "axios";

let usuario;
if (sessionStorage.usuario) {
  usuario = JSON.parse(sessionStorage.usuario);
}

export default {
  name: "Panel",
  data() {
    return {
      usuario,
    };
  },
  methods: {
    logout() {
      sessionStorage.removeItem("usuario");
      this.$router.go("/");
    },
    navigate(path) {
      this.$router.push(path);
    },
  },
  async beforeMount() {
    await axios
      .get(`${this.$store.getters.getApiURL}/atv/abertas?nome=${this.$store.getters.getUsuario.nome}`, {
        headers: {
          "Access-Control-Allow-Origin": "*",
          Authorization: `Bearer ${this.$store.getters.getToken}`,
        },
      })
      .then((atividades) => {
        if (atividades.data.length >= 1) {
          this.$store.commit("setAtv", atividades.data.length);
          return;
        }
      });
  },
};
</script>

<style>
body {
  background: white;
}
textarea.materialize-textarea:focus {
  border-bottom: 1px solid #66bb6a !important;
}
.menuActive {
  border-right: 10px solid #66bb6a;
  background-color: #78909c !important;
}
</style>
