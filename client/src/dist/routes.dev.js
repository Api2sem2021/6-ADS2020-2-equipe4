"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _vueRouter = require("vue-router");

var _Login = _interopRequireDefault(require("./components/Login.vue"));

var _Panel = _interopRequireDefault(require("./components/Panel.vue"));

var _Notifications = _interopRequireDefault(require("./components/Notifications.vue"));

var _Chat = _interopRequireDefault(require("./components/Chat.vue"));

var _Employes = _interopRequireDefault(require("./components/Employes.vue"));

var _History = _interopRequireDefault(require("./components/History.vue"));

var _InternalChat = _interopRequireDefault(require("./components/InternalChat.vue"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

var routes = [{
  path: '/login',
  name: 'Login',
  component: _Login["default"]
}, {
  path: '/panel',
  name: 'Panel',
  component: _Panel["default"],
  children: [{
    path: '/panel/activities',
    name: 'Notifications',
    component: _Notifications["default"]
  }, {
    path: '/panel/live_chats',
    name: 'Chat',
    component: _Chat["default"]
  }, {
    path: '/panel/employes',
    name: 'Employes',
    component: _Employes["default"]
  }, {
    path: '/panel/history',
    name: 'History',
    component: _History["default"]
  }, {
    path: '/panel/internalChat',
    name: 'InternalChat',
    component: _InternalChat["default"]
  }]
}, {
  path: '/',
  redirect: {
    path: '/panel/activities'
  }
}];
var router = (0, _vueRouter.createRouter)({
  history: (0, _vueRouter.createWebHistory)(),
  routes: routes
});
router.beforeEach(function (to, from, next) {
  if (to.name !== 'Login' && !sessionStorage.usuario) {
    next({
      path: '/login'
    });
  } else if (to.name == 'Login' && sessionStorage.usuario) {
    next({
      path: '/panel/activities'
    });
  } else next();
});
var _default = router;
exports["default"] = _default;