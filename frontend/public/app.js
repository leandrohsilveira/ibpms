riot.mount('*');
numeral.locale('pt-br');

window.ibpmsDefaultSimpleMaskMoneyConfig = {
    "allowNegative": false,
    "negativeSignAfter": false,
    "decimalSeparator": ",",
    "fixed": true,
    "fractionDigits": 2,
    "prefix": "",
    "suffix": "",
    "thousandsSeparator": ".",
    "cursor": "end"
};

window.ibpms_dispatchEvent = function (type, payload) {
    const event = document.createEvent('CustomEvent');
    event.initCustomEvent(type, false, false, payload);
    document.dispatchEvent(event);
};