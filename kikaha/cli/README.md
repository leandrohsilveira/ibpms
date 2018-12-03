# Kikaha CLI

Docker image to create containers to execute CLI commands:

## Create a project

```
docker container run --rm \ 
    -v ${pwd}:/home/kikaha \
    -w /home/kikaha \
    leandrohsilveira/kikaha-cli \
    bash --login -c "kikaha project create 2.1 --group-id=<<GROUP-ID>> --artifact-id=<<ARTIFACT-ID>>"
```

## Build

```
docker container run --rm \ 
    -v ${pwd}:/home/kikaha \
    -w /home/kikaha \
    leandrohsilveira/kikaha-cli \
    bash --login -c "kikaha build <<ARTIFACT-ID>>"
```

## Interactive mode:

```
docker container run -it --rm \ 
    -v ${pwd}:/home/kikaha \
    -w /home/kikaha \
    leandrohsilveira/kikaha-cli \
    bash --login
```