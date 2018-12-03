# Kikaha Server

Image to create containers that starts a kikaha project server.

## Volumes

- **Project folder**: `/home/kikaha/<<ARTIFACT-ID>>`
- **Maven cache**: `/root/.m2`

## Start server

```
docker container run --rm \ 
    -v ${pwd}:/home/kikaha \
    -w /home/kikaha \
    -e MODULE_NAME=<<ARTIFACT-ID>> \
    leandrohsilveira/kikaha-server
```