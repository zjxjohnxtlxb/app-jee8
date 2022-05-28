#!/bin/sh
if [ $(docker ps -a -f name=app-jee8 | grep -w app-jee8 | wc -l) -eq 1 ]; then
  docker rm -f app-jee8
fi
mvn clean package && docker build -t fr.cours.jee/app-jee8 .
docker run -d -p 9080:9080 -p 9443:9443 --name app-jee8 fr.cours.jee/app-jee8
