import riot from 'riot';
import route from 'riot-route';
import numeral from 'numeral';

import './polyfills';

import 'numeral/locales/pt-br.js';
import 'normalize.css/normalize.css';
import 'milligram/dist/milligram.css';

import './app/app.tag';

document.addEventListener('DOMContentLoaded', () => {
    var g = window || global;
    g.route = riotRoute.default;
    
    numeral.locale('pt-br');
    
    riot.mount('*');
    
    g.route.base('/');
});
