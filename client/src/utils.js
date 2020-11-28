export default {
    dateFormat(date) {
        let data;
        if (date) {
            data = new Date(date);
        } else {
            data = new Date();
        }

        return `${data.getDay()}/${data.getMonth()}/${data.getFullYear()}`
    },

    hourFormat(hour) {
        let data = new Date()
        if (hour) {
            data.setTime(hour);
        }
        let dia = data.getHours()
        if (dia.toString().length == 1) { dia = `0${dia}` }
        return `${dia}:${data.getMinutes()}:${data.getSeconds()}`
    }
}