"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _vuex = require("vuex");

var state = {
  usuario: JSON.parse(sessionStorage.getItem('usuario')) || {},
  token: sessionStorage.getItem('token') || "",
  apiUrl: "http://localhost:8081/spring-app"
};
var mutations = {};

var _default = (0, _vuex.createStore)({
  state: state,
  mutations: mutations
});

exports["default"] = _default;