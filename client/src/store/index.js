import {createStore} from 'vuex';

const state = {
    usuario: JSON.parse(sessionStorage.getItem('usuario')) || {},
    token: sessionStorage.getItem('token') || "",
    apiUrl: "http://localhost:8081/spring-app",
    section: "activities",
    atividadesAbertas: 0
};

const mutations = {
    setUser(state, usuario){
        state.usuario = usuario
    },
    setToken(state, token){
        state.token = token
    },
    setSection(state, section){
        state.section = section;
    },
    setAtv(state,number){
        state.atividadesAbertas = number;
    }
}

const getters = {
    getToken: state =>{
        return state.token;
    },
    getUsuario: state =>{
        return state.usuario;
    },
    getApiURL: state =>{
        return state.apiUrl;
    },
    getSection: state =>{
        return state.section;
    },
    getAtvAbertas: state =>{
        return state.atividadesAbertas;
    }
}

export default createStore({
    state,
    mutations,
    getters
})