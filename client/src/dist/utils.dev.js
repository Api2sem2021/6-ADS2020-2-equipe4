"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;
var _default = {
  dateFormat: function dateFormat(date) {
    var data;

    if (date) {
      data = new Date(date);
    } else {
      data = new Date();
    }

    return "".concat(data.getDate(), "/").concat(data.getMonth() + 1, "/").concat(data.getFullYear());
  },
  hourFormat: function hourFormat(hour) {
    var data = new Date();

    if (hour) {
      data.setTime(hour);
    }

    var dia = data.getHours();

    if (dia.toString().length == 1) {
      dia = "0".concat(dia);
    }

    return "".concat(dia, ":").concat(data.getMinutes(), ":").concat(data.getSeconds());
  }
};
exports["default"] = _default;