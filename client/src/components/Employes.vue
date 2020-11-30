<template>
  <div>
    <div class="row">
      <div class="col s12">
        <div class="card blue-grey darken-1 pop">
          <div class="card-content white-text">
            <span class="card-title" style="padding-block: 5px"><i class="material-icons">group</i> Funcionários</span>
            <div class="row blue-grey">
              <div class="container">
                <div class="col s12 center-align">
                  <div class="row">
                    <div class="input-field col s10">
                      <i class="material-icons prefix">search</i>
                      <input v-model="nomeBusca" v-on:keyup.enter="filter_hub()" type="text" class="validate" />
                      <label for="icon_prefix2" class="white-text">Pesquisar</label>
                      <span class="left-align helper-text white-text">Buscar por nome</span>
                    </div>
                    <a v-on:click="filter_hub()" style="margin-top: 20px" class="btn small col s2 green"><i class="material-icons">search</i></a>
                    <p class="col s6">
                      <label>
                        <input class="red" type="checkbox" v-model="permissoes" value="Diretores" v-on:change="filter_hub()" />
                        <span class="white-text">Diretores</span>
                      </label>
                    </p>
                    <p class="col s6">
                      <label>
                        <input type="checkbox" v-model="permissoes" value="Atendentes" v-on:change="filter_hub()"/>
                        <span class="white-text">Atendentes</span>
                      </label>
                    </p>
                  </div>
                </div>
              </div>

              <div v-for="(usuario, key) in usuariosFiltrados" v-bind:key="usuario.id" v-bind:id="key">
                <div v-if="this.$store.getters.getUsuario.id != usuario.id" class="col s12 m3 black-text pop hoverable" style="cursor: pointer">
                  <div
                    class="card horizontal modal-trigger"
                    v-on:click="
                      () => {
                        this.selectedUser = usuario;
                      }
                    "
                    href="#modal_usuario"
                  >
                    <div class="card-image">
                      <img v-if="usuario.avatar != null" v-bind:src="usuario.avatar" style="height: 120px; width: 100px" />
                      <img v-else src="../assets/profile.png" style="height: 120px; width: 100px" />
                    </div>
                    <div class="card-stacked">
                      <div class="card-content center-align">
                        <p>{{ usuario.nome }}</p>
                        <br />
                        <p v-if="usuario.autorizacoes[0].nome == 'ROLE_ADMIN'">Diretor</p>
                        <p v-else>Atendente</p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div id="modal_usuario" class="modal" style="width: 250px">
      <div class="modal-content center-align blue-grey darken-1" style="height: auto">
        <div class="col s12 m8 offset-m2 l6 offset-l3">
          <div class="blue-grey lighten-2">
            <div class="row" style="padding: 10px">
              <div class="col s12">
                <img v-if="selectedUser && selectedUser.avatar != null" v-bind:src="selectedUser.avatar" style="height: 220px; width: 220px" class="circle responsive-img" />
                <img v-else src="../assets/profile.png" class="circle responsive-img" />
              </div>
              <div class="col s12" style="padding: 15px; overflow-y: hidden !important">
                <span v-if="selectedUser" class="black-text" style="font-size: 1.5em"> {{ selectedUser.nome }} </span>
                <br />
                <span v-if="selectedUser && selectedUser.autorizacoes[0].nome == 'ROLE_ADMIN'" class="black-text"> Diretor </span>
                <span v-else class="black-text"> Atendente </span>
              </div>
              <div class="col s12">
                <a class="btn-small blue" style="margin-bottom: 5px">Enviar Mensagem</a>
                <br />
                <a v-if="this.$store.getters.getUsuario.autorizacoes[0].nome == 'ROLE_ADMIN'" href="#modal_atividade" class="btn-small modal-trigger orange">Agendar Atividade</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div id="modal_atividade" class="modal" style="height: auto">
      <div class="modal-content blue-grey darken-1">
        <h4 class="white-text">Atividade</h4>
        <hr />
        <div class="row">
          <form class="col s12">
            <div class="row">
              <div class="input-field col s6">
                <i class="material-icons prefix white-text">title</i>
                <input v-model="atividadeTitulo" id="atividade_titulo" type="text" data-length="10" class="white-text" />
                <label for="input_text">Título</label>
              </div>
              <div class="input-field col s6" style="height: auto">
                <i class="material-icons prefix white-text">date_range</i>
                <input id="data_agendamento" type="text" class="datepicker white-text" />
                <label for="icon_prefix2">Data</label>
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <i class="material-icons prefix white-text">mode_edit</i>
                <textarea v-model="atividadeConteudo" style="border-bottom: 1px solid #66bb6a; box-shadow: 0 1px 0 0 #66bb6a" id="atividade_conteudo" class="materialize-textarea white-text" data-length="120"></textarea>
                <label for="textarea2">Conteúdo</label>
              </div>
            </div>
          </form>
        </div>
      </div>
      <div class="modal-footer blue-grey darken-1">
        <a class="btn red col s6 modal-close" style="margin-right: 5px">Fechar</a>
        <a class="btn green col s6" v-on:click="postAtividade()">Agendar</a>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import M from "materialize-css";

export default {
  name: "Employes",
  data() {
    return {
      usuarios: [],
      usuariosFiltrados: [],
      selectedUser: null,
      atividadeTitulo: "",
      atividadeConteudo: "",
      dataAgendamento: "",
      nomeBusca: "",
      permissoes: []
    };
  },
  methods: {
    async buscarUsuarios() {
      await axios
        .get(`${this.$store.getters.getApiURL}/usuario`, {
          headers: {
            "Access-Control-Allow-Origin": "*",
            Authorization: `Bearer ${this.$store.getters.getToken}`,
          },
        })
        .then((usuarios) => {
          usuarios = usuarios.data;
          if (usuarios.length >= 1) {
            this.usuarios = usuarios;
            return;
          }
          M.toast({ html: "<p class='red-text'>Nenhum usuario asdencontrado</p>" });
        })
        .catch(() => {
          M.toast({ html: "<p class='red-text'>Nenhum usuario encontrado</p>" });
        });
    },
    async postAtividade() {
      let dataDisparo = new Date();
      dataDisparo = `${dataDisparo.getFullYear()}-${dataDisparo.getMonth() + 1}-${dataDisparo.getDate()}`;
      let dataAgendada = document.getElementById("data_agendamento").value;
      await axios
        .post(
          `${this.$store.getters.getApiURL}/atv`,
          {
            titulo: this.atividadeTitulo,
            conteudo: this.atividadeConteudo,
            nomeRemetente: this.$store.getters.getUsuario.nome,
            nomeDestinatario: this.selectedUser.nome,
            dataDisparo,
            dataAgendada: `${dataAgendada.slice(6)}-${dataAgendada.slice(3, 5)}-${dataAgendada.slice(0, 2)}`,
            status: "1",
          },
          {
            headers: {
              "Access-Control-Allow-Origin": "*",
              Authorization: `Bearer ${this.$store.state.token}`,
            },
          }
        )
        .then(() => {
          this.atividadeTitulo = this.atividadeConteudo = this.dataAgendamento;
          document.getElementById("data_agendamento").value = "";
          M.toast({
            html: '<p class="green-text">Atividade agendada com sucesso!</p>',
          });
        })
        .catch(() => {
          M.toast({ html: "<p class='red-text'>Erro ao agendar a notificação, verifique os campos</p>" });
        });
    },
    buscarPorNome() {
      let usuariosFiltradosPorNome = [];
      if (this.nomeBusca && this.nomeBusca != "") {
        this.usuariosFiltrados.forEach((usuario) => {
          if (usuario.nome.toLowerCase().includes(this.nomeBusca.toLowerCase())) {
            usuariosFiltradosPorNome.push(usuario);
          }
        });
        this.usuariosFiltrados = usuariosFiltradosPorNome;
        return;
      }
      return;
    },
    buscarUsuariosPorPermissoes() {
      let usuariosFiltradosPermissao = [];
      if (this.permissoes.indexOf("Diretores") != -1 || this.permissoes.indexOf("Atendentes") != -1) {

        if (this.permissoes.indexOf("Diretores") != -1) {
          this.usuariosFiltrados.forEach((usuario) => {
            if (usuario.autorizacoes[0].nome == "ROLE_ADMIN") {
              usuariosFiltradosPermissao.push(usuario);
            }
          });
        }

        if (this.permissoes.indexOf("Atendentes") != -1) {
          this.usuariosFiltradosPermissao.forEach((usuario) => {
            if (usuario.autorizacoes[0].nome == "ROLE_USUARIO") {
              usuariosFiltradosPermissao.push(usuario);
            }
          });
        }

        this.usuariosFiltrados = usuariosFiltradosPermissao;
      }
    },
    filter_hub() {
      this.usuariosFiltrados = this.usuarios;

      if (this.nomeBusca && this.nomeBusca != "") {
        this.buscarPorNome();
      }
      if (this.permissoes.length >= 1) {
        this.buscarUsuariosPorPermissoes();
      }
    }
  },
  async beforeMount() {
    await this.buscarUsuarios();
    this.usuariosFiltrados = this.usuarios
  },
  mounted() {
    let calendar_options = {
      format: "dd/mm/yyyy",
      i18n: {
        months: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
        monthsShort: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
        weekdays: ["Sábado", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Domingo"],
        weekdaysShort: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"],
        weekdaysAbbrev: ["D", "S", "T", "Q", "Q", "S", "S"],
        cancel: "Fechar",
        clear: "Limpar",
        done: "Confirmar",
      },
    };
    M.Datepicker.init(document.querySelectorAll(".datepicker"), calendar_options);
    M.Modal.init(document.querySelectorAll(".modal"));
  },
};
</script>

<style>
.datepicker-date-display {
  background-color: #66bb6a;
}

.datepicker-cancel {
  color: red;
}

.datepicker-done {
  color: #66bb6a;
}

.datepicker-table td.is-selected {
  background-color: #66bb6a;
}

.modal .datepicker-modal {
  width: 65%;
}
</style>
