import riot from 'riot';
import route from 'riot-route';
import numeral from 'numeral';

import './polyfills';

import 'numeral/locales/pt-br.js';
import 'milligram/dist/milligram.min.css';
import 'normalize.css/normalize.css';

import './app/app.tag';

numeral.locale('pt-br');

riot.mount('*');

route.base('/');