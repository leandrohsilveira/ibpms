import 'whatwg-fetch';
import 'promise-polyfill/src/polyfill';

// https://github.com/uxitten/polyfill/blob/master/string.polyfill.js
// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/padEnd
if (!String.prototype.padEnd) {
    String.prototype.padEnd = function padEnd(targetLength,padString) {
        targetLength = targetLength>>0; //floor if number or convert non-number to 0;
        padString = String((typeof padString !== 'undefined' ? padString : ' '));
        if (this.length > targetLength) {
            return String(this);
        }
        else {
            targetLength = targetLength-this.length;
            if (targetLength > padString.length) {
                padString += padString.repeat(targetLength/padString.length); //append to original to ensure we are longer than needed
            }
            return String(this) + padString.slice(0,targetLength);
        }
    };
}

if (!String.prototype.repeat) {
    String.prototype.repeat = function(count) {
      'use strict';
      if (this == null) {
        throw new TypeError('não é possível converter ' + this + ' para um objeto');
      }
      var str = '' + this;
      count = +count;
      if (count != count) {
        count = 0;
      }
      if (count < 0) {
        throw new RangeError('o núm. de repetições não pode ser negativo');
      }
      if (count == Infinity) {
        throw new RangeError('o núm. de repetições deve ser menor que infinito');
      }
      count = Math.floor(count);
      if (str.length == 0 || count == 0) {
        return '';
      }
  
      // Garantir que count seja um inteiro de 31 bits nos dá uma grande otimização
      // na parte principal. Porém, navegadores atuais (de agosto de 2014 pra cá)
      // não conseguem mais manipular strings de 1 << 28 chars ou maiores, então:
      if (str.length * count >= 1 << 28) {
        throw new RangeError('o núm. de repetições não deve estourar o tamanho máx. de uma string');
      }
      var rpt = '';
      for (var i = 0; i < count; i++) {
        rpt += str;
      }
      return rpt;
    }
  }