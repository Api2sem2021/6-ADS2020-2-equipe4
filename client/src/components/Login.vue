<template>
  <div class="container pop" style="margin-top: 10%">
    <div class="row">
      <div class="col s12 m6 l6 offset-m3 offset-l3">
        <div class="card">
          <div class="card-panel blue-grey darken-1">
            <div class="row">
              <form class="col s12 white-text">
                <div class="row">
                  <div class="input-field col s12">
                    <i class="material-icons prefix">person</i>
                    <input v-model="name" type="text" class="validate white-text" v-on:keyup.enter="login" />
                    <label for="name">Nome</label>
                  </div>
                  <div class="input-field col s12">
                    <i class="material-icons prefix">lock</i>
                    <input v-model="password" type="password" class="validate white-text" v-on:keyup.enter="login" />
                    <label for="password">Senha</label>
                  </div>
                  <div class="section"></div>
                  <a v-on:click="login()" class="btn waves-effect waves-light col s12 green lighten-1">Entrar</a>
                </div>
                <div class="input-field col s6 m6 l6">
                  <p class="medium-small"><a href="#modal1" class="modal-trigger">Esqueceu a senha?</a></p>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div id="modal1" class="modal" style="width: 40% !important; display: none; max-height: 100%">
      <div class="modal-content">
        <div class="row">
          <div class="col s12">
            <h4 class="center-align">Redefinir senha</h4>
            <p class="green lighten-1 white-text z-depth-2" style="padding: 5px">
              Preencha o campo abaixo com o e-mail definido no seu cadastro.<br />
              Logo após confirmar o envio um e-mail com um link de redefinição de senha será enviado para o e-mail preenchido.
            </p>
          </div>
          <div class="input-field col s12">
            <i class="material-icons prefix">email</i>
            <input id="emailForgot" type="email" class="validate" />
            <label for="emailForgot">E-mail</label>
          </div>
          <a href="#!" class="col s12 modal-close waves-effect waves-green btn green lighten-1 center-align">Confirmar</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import M from "materialize-css";

export default {
  name: "Login",
  data() {
    return {
      name: "",
      password: "",
    };
  },
  mounted() {
    M.Modal.init(document.querySelectorAll(".modal"));
  },
  methods: {
    login() {
      axios
        .post(
          `http://localhost:8081/spring-app/login`,
          {
            username: this.name,
            password: this.password,
          },
          {
            headers: {
              "Access-Control-Allow-Origin": "*",
            },
          }
        )
        .then(async (data) => {
          let usuario = data.data;
          sessionStorage.setItem("token", usuario.token);
          this.$store.state.token = usuario.token;

          await axios
            .get(`${this.$store.getters.getApiURL}/usuario/nome?nome=${usuario.username}`, {
              headers: {
                "Access-Control-Allow-Origin": "*",
                Authorization: `Bearer ${usuario.token}`,
              },
            })
            .then((usuario) => {
              sessionStorage.setItem("usuario", JSON.stringify(usuario.data));
              this.name = this.password = "";
              this.$router.go("/panel/");
            })
        }).catch(() => {
              M.toast({ html: "<p class='red-text'>Usuario não encontrado</p>" });
            });
    },
  },
};
</script>

<style>
body {
  background: #66bb6a !important;
}
</style>
