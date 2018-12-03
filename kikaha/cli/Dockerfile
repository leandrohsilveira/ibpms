FROM openjdk:8

RUN apt-get update && \
    apt-get install curl && \
    bash --login -c "curl -s http://download.kikaha.io/installer | bash" && \
    bash --login -c "kikaha setup"