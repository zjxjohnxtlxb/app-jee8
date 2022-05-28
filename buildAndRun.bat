@echo off
call mvn clean package
call docker build -t fr.cours.jee/app-jee8 .
call docker rm -f app-jee8
call docker run -d -p 9080:9080 -p 9443:9443 --name app-jee8 fr.cours.jee/app-jee8