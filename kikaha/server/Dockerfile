FROM leandrohsilveira/kikaha-cli

RUN apt-get install -y gettext

ENV MODULE_NAME=app
ENV SERVER_HTTP_PORT=9000
ENV SERVER_HTTPS_PORT=9001

WORKDIR /home/kikaha

EXPOSE ${SERVER_HTTP_PORT}
EXPOSE ${SERVER_HTTPS_PORT}

CMD bash --login -c "kikaha run_app ${MODULE_NAME}"