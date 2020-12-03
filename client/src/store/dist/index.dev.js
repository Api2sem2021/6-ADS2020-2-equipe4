"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _vuex = require("vuex");

var state = {
  usuario: JSON.parse(sessionStorage.getItem('usuario')) || {},
  token: sessionStorage.getItem('token') || "",
  apiUrl: "http://localhost:8081/spring-app",
  section: "activities",
  atividadesAbertas: 0
};
var mutations = {
  setUser: function setUser(state, usuario) {
    state.usuario = usuario;
  },
  setToken: function setToken(state, token) {
    state.token = token;
  },
  setSection: function setSection(state, section) {
    state.section = section;
  },
  setAtv: function setAtv(state, number) {
    state.atividadesAbertas = number;
  }
};
var getters = {
  getToken: function getToken(state) {
    return state.token;
  },
  getUsuario: function getUsuario(state) {
    return state.usuario;
  },
  getApiURL: function getApiURL(state) {
    return state.apiUrl;
  },
  getSection: function getSection(state) {
    return state.section;
  },
  getAtvAbertas: function getAtvAbertas(state) {
    return state.atividadesAbertas;
  }
};

var _default = (0, _vuex.createStore)({
  state: state,
  mutations: mutations,
  getters: getters
});

exports["default"] = _default;