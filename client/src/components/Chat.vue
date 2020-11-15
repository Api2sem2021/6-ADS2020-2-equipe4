<template>
  <div class="row">
    <div class="col s12 m12">
      <div class="card blue-grey darken-1" style="">
        <div class="card-content">
          <div class="row" style="height: 500px">
            <div
              class="blue-grey lighten-1 col s12 m4 l4"
              style="height: 500px"
            >
              <div
                class="green lighten-1 white-text z-depth-2"
                style="margin: 10px"
              >
                <div style="padding: 10px">
                  <span class="card-title center-align"
                    ><i class="material-icons">arrow_downward</i>Conversas ao
                    vivo</span
                  >
                  <p class="center-align">
                    As conversas listadas abaixo estão acontecendo em tempo real
                  </p>
                </div>
              </div>
              <ul class="collection z-depth-2">
                <li class="collection-item avatar pop li-active">
                  <img
                    src="data:image/jpeg;base64,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"
                    alt=""
                    class="circle z-depth-2"
                  />
                  <strong class="title green-text lighten-1"
                    >Dando Boura</strong
                  >
                  <p class="truncate">
                    Lorem ipsum dolor sit amet consectetur adipisicing elit.
                    Officiis, labore?
                  </p>
                  <span
                    class="secondary-content green-text lighten-1"
                    style="font-weight: bolder"
                    >12:00</span
                  >
                </li>
                <li class="collection-item avatar pop">
                  <img
                    src="data:image/jpeg;base64,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"
                    alt=""
                    class="circle z-depth-2"
                  />
                  <strong class="title green-text lighten-1"
                    >Mickey Moura</strong
                  >
                  <p class="truncate">
                    Lorem ipsum dolor sit amet consectetur adipisicing elit.
                    Officiis, labore?
                  </p>
                  <span
                    class="secondary-content green-text lighten-1"
                    style="font-weight: bolder"
                    >12:00</span
                  >
                </li>
                <li class="collection-item avatar pop">
                  <img
                    src="https://images.uncyc.org/pt/thumb/3/37/Paretooo.jpg/300px-Paretooo.jpg"
                    alt=""
                    class="circle z-depth-2"
                  />
                  <strong class="title green-text lighten-1"
                    >Luiz Pareto</strong
                  >
                  <p class="truncate">
                    Lorem ipsum dolor sit amet consectetur adipisicing elit.
                    Officiis, labore?
                  </p>
                  <span
                    class="secondary-content green-text lighten-1"
                    style="font-weight: bolder"
                    >12:00</span
                  >
                </li>
                <li class="collection-item avatar pop">
                  <img
                    src="https://comunhao.com.br/wp-content/uploads/2018/02/Xaropinho-1-e1519213937248.jpg"
                    alt=""
                    class="circle z-depth-2"
                  />
                  <strong class="title green-text lighten-1">Xaropinho</strong>
                  <p class="truncate">
                    Lorem ipsum dolor sit amet consectetur adipisicing elit.
                    Officiis, labore?
                  </p>
                  <span
                    class="secondary-content green-text lighten-1"
                    style="font-weight: bolder"
                    >12:00</span
                  >
                </li>
                <li class="collection-item avatar pop">
                  <div class="hoverableli">
                    <img
                      src="https://www.vagalume.com.br/pepe-moreno/images/pepe-moreno.jpg"
                      alt=""
                      class="circle z-depth-2"
                    />
                    <strong class="title green-text lighten-1"
                      >Pepe Moreno</strong
                    >
                    <p class="truncate">
                      Lorem ipsum dolor sit amet consectetur adipisicing elit.
                      Officiis, labore?
                    </p>
                    <span
                      class="secondary-content green-text lighten-1"
                      style="font-weight: bolder"
                      >12:00</span
                    >
                  </div>
                </li>
              </ul>
            </div>

            <nav class="col s12 m8 l8 green lighten-1">
              <div class="nav-wrapper">
                <a href="#!" class="brand-logo"
                  ><i
                    src="https://www.vagalume.com.br/pepe-moreno/images/pepe-moreno.jpg"
                    class="material-icons"
                    >person</i
                  >Dando Boura</a
                >
                <ul class="right hide-on-med-and-down">
                  <li>
                    <a><i class="material-icons">more_vert</i></a>
                  </li>
                </ul>
              </div>
            </nav>
            <div class="col s12 m8 l8" style="padding: 0px !important">
              <div class="green lighten-3 chat-wrapper">
                <div class="col l12">
                  <div class="col s8 m4 l4 white z-depth-3 message pop">
                    <p class="left-align">
                      Lorem ipsum dolor sit amet consectetur adipisicing elit.
                      Quod, voluptate.
                    </p>

                    <p class="right-align green-text lighten-1">12:00</p>
                  </div>
                </div>
                <div class="col l12">
                  <div class="col s8 m4 l4 white z-depth-3 message pop">
                    <p class="left-align">
                      Lorem ipsum dolor sit amet consectetur adipisicing elit.
                      Quod, voluptate.
                    </p>

                    <p class="right-align green-text lighten-1">12:00</p>
                  </div>
                </div>

                <div
                  class="col s8 m4 l4 offset-s4 offset-m8 offset-l8 white z-depth-3 message pop"
                >
                  <p class="left-align">
                    Lorem ipsum, dolor sit amet consectetur adipisicing elit.
                    Error dicta facere beatae nihil veritatis nulla at vero
                    porro dolorem, illum sed tempore sapiente veniam debitis aut
                    totam magnam, consectetur obcaecati!
                  </p>
                  <p class="right-align green-text lighten-1">12:00</p>
                </div>
                <div
                  class="col s8 m4 l4 offset-s4 offset-m8 offset-l8 white z-depth-3 message pop"
                >
                  <p class="left-align">
                    Lorem ipsum, dolor sit amet consectetur adipisicing elit.
                    Error dicta facere beatae nihil veritatis nulla at vero
                    porro dolorem, illum sed tempore sapiente veniam debitis aut
                    totam magnam, consectetur obcaecati!
                  </p>
                  <p class="right-align green-text lighten-1">12:00</p>
                </div>
              </div>

              <div
                class="blue-grey lighten-1 col s12 z-depth-5"
                style="height: 68.5px"
              >
                <div class="input-field col s8 search-fiel white-text">
                  <i class="material-icons prefix">edit</i>
                  <input type="text" class="validate white-text" />
                  <label for="search" class="white-text"
                    >Digite a sua mensagem</label
                  >
                </div>
                <a
                  class="col s4 btn-large waves-effect green lighten-1"
                  style="margin-top: 5px"
                  ><i class="material-icons left">send</i>Enviar</a
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Chat",
};
</script>

<style>
.avatar-message {
  margin-top: 5px !important;
}
.message {
  width: 270px;
  margin-top: 5px;
  padding: 15px !important;
}

.chat-wrapper {
  overflow-y: hidden;
  padding-left: 15px;
  padding-right: 15px;
  height: 370px;
}

.chat-wrapper:hover {
  overflow-y: auto;
}
.hoverableli :hover {
  background-color: #ddd;
}
strong {
  font-weight: bolder;
}

.rounded {
  border-radius: 25px;
}

.collection {
  height: 350px !important;
  border: none;
}
.collection:hover {
  overflow-y: auto;
}

.li-active {
  border-right: 5px solid #66bb6a;
  background-color: #ddd !important;
}
</style>
