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

function dispatchEvent(type, payload) {
    const event = document.createEvent('CustomEvent');
    event.initCustomEvent(type, false, false, payload);
    document.dispatchEvent(event);
}

window.ibpms = {

    message: {
        dispatch: function(message) {
            dispatchEvent('ibpms.message.set', message);
        },
        subscribe: function(listener) {
            document.addEventListener('ibpms.message.set', eventListener);
            return function() {
                document.removeEventListener('ibpms.message.set', eventListener);
            };

            function eventListener(e) {
                listener(e.detail);
            }
        }
    }

}

riot.mount('*');