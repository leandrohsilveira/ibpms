# Preparando o ambiente

* Instalar o [docker](https://www.docker.com/get-started);
* Verificar se o comando `docker-compose` está disponível, senão, instalar o [docker compose](https://docs.docker.com/compose/install/#prerequisites).

# Executar

1. Abrir um terminal de comando no diretorio raiz, onde o arquivo `docker-compose.yml` está;
2. Criar uma cópia do arquivo `.env.template` chamada `.env` no mesmo diretório. Este arquivo está mapeado no `.gitignore`.
3. Executar o comando `docker-compose up -d`;
4. Acessar a página: `http://localhost`.

# Containers

* **backend-database**: O banco de dados do backend, baseado na imagem [PostgreSQL](https://hub.docker.com/_/postgres/);
* **backend**: Servidor do backend da aplicação, baseada numa imagem criada para o projeto [leandrohsilveira/kikaha-server](https://hub.docker.com/r/leandrohsilveira/kikaha-server/);
* **frontend**: Servidor que serve as páginas e o conteúdo estático, baseado na imagem do [NGINX](https://hub.docker.com/_/nginx/);
* **proxy**: Servidor proxy para rotear requisições entre o frontend e o backend, baseado na imagem do NGINX.

## Backend

Foi utilizado o projeto [Kikaha](http://kikaha.io) para construir o backend.
O projeto é constituido das camadas:
* **Resource**: Os mapeamentos dos endpoints REST;
    * ProductResource;
* **Service**: Responsável por fornecer e gerenciar a conexão com o banco de dados;
    * ModelService;
    * ProductService;
* **DAO**: Responsável por consumir uma conexão com o banco de dados e realizar as operações de cadastro, atualização, consulta e exclusão de registros do banco de dados.
    * ModelDAO;
    * ProductDAO;

### Configurações

Para permitir a configuração das propriedades do Kikaha, foi implementado um config enrichment para ler configurações do arquivo `/home/kikaha/ibpms/env.yml`, que é produzido pelo container a partir do arquivo de template `/home/kikaha/ibpms/env.template.yml` utilizando a ferramenta [envsubst](https://www.gnu.org/software/gettext/manual/html_node/envsubst-Invocation.html) para substituir expressões de variáveis de ambiente.

### Documentação da API

Foi publicada uma documentação da API REST através do Postman, para acessá-la, clique [aqui](https://documenter.getpostman.com/view/125286/RzffLqYR).

## Frontend

Foi utilizado o [Riot.JS](https://riot.js.org/) para construir a aplicação do tipo Single Page App e o [Milligram](https://milligram.io/) para estilização básica do layout.

## Proxy

O proxy atua roteando as rotas da seguinte maneira:
* Requisições para **/api/\*** é mapeado para o **backend** no caminho **/\***
* Requisições para **/\*** é mapeado para o **frontend** no caminho **/\***

# Ambientes

## Produção

Basta executar o comando `docker-compose up -d` na raiz do projeto e esperar.

## Desenvolvimento

Abrir dois terminais de comando na raiz do projeto.

1. No primeiro terminal, executar o comando `docker-compose -f docker-compose.yml -f docker-compose.dev.yml up -d`;
2. No segundo terminal, executar os comandos:
    1. `cd frontend` para navegar para o diretorio do frontend.
    2. `npm i` para instalar as dependencias de desenvolvimento.
    3. `npm start` para iniciar a ferramenta do `riot` em modo "watcher".

Ao salvar algum arquivo dentro da pasta `frontend/public` e `frontend/src`, as mudanças serão aplicadas imediatamente.