import {createStore} from 'vuex';

const state = {
    usuario: sessionStorage.getItem('usuario') || {},
    token: sessionStorage.getItem('token') || ""
};

const mutations = {
}

export default createStore({
    state,
    mutations
})