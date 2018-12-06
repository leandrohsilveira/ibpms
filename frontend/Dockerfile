FROM node:10.14.1 as build

COPY . /home/app

WORKDIR /home/app

RUN npm i && npm run build

FROM nginx:1.15.7-alpine

COPY ./default.conf /etc/nginx/conf.d/default.conf
COPY --from=build /home/app/public /usr/share/nginx/html

CMD exec nginx -g 'daemon off;'