import {createStore} from 'vuex';

const state = {
    usuario: sessionStorage.getItem('usuario') || {},
    token: sessionStorage.getItem('token') || "",
    apiUrl: "http://localhost:8081/spring-app"
};

const mutations = {
}

export default createStore({
    state,
    mutations
})