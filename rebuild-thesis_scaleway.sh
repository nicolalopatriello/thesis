#!/bin/bash

git pull origin main
cd docker-env/develop/services/
make down
cd ..
cd microservices/
make down
cd ..
cd ..
cd ..
docker rmi -f nik90/thesis-ui:1.0.0...
docker rmi -f nik90/thesis-core:1.0.0...
./create-dockers.sh
cd thesis-ui/
ng build --prod && docker build -t nik90/thesis-ui:1.0.0... .
cd ..
cd docker-env/develop/services/
make up
cd ..
cd microservices/
make up
