import riot from 'riot';
import 'riot-hot-reload';

const importAll = (r) => r.keys().forEach(r)

const context = require.context('./', true, /\.tag$/i);

importAll(context);

riot.mount('*');