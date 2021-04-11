#!/bin/bash
ACTUAL_DIR=${PWD}
INSTALLATION_DIR=$ACTUAL_DIR/compose
NETWORK_NAME=kirk
REVISION=$(cd ../../../ && mvn help:evaluate -Dexpression=revision -q -DforceStdout)

function createDockerNetwork(){
	NETWORK_EXIST=$(docker network ls | grep "$NETWORK_NAME")

	SUBNET=172.8.0.0/16
	RANGE=172.8.1.0/24
	GATEWAY=172.8.1.254
	if [ -z "$NETWORK_EXIST" ]; then
		echo "Create docker network $NETWORK_NAME"
	
		if docker network create --driver=bridge --subnet=$SUBNET --ip-range=$RANGE --gateway=$GATEWAY $NETWORK_NAME ; 			then 
			echo "Creation DONE!"
			echo "Newtwork has been created with following props:"
			echo "subnet=$SUBNET ip-range=$RANGE gateway=$GATEWAY name=$NETWORK_NAME"
		else
			echo "Creation FAIL!"
		fi
	else
		echo "Network $NETWORK_NAME already exists!"
	fi
}

function prepareDockerCompose(){
	rm -rf $INSTALLATION_DIR
	mkdir -p $INSTALLATION_DIR
	cp docker-compose-template.yml $INSTALLATION_DIR/docker-compose.yml
	cp ../.env $INSTALLATION_DIR

	cd $INSTALLATION_DIR
	sed -i -e 's#<CURR_DIR>#'$ACTUAL_DIR'#g' docker-compose.yml
	sed -i -e 's#<REVISION>#'$REVISION'#g' docker-compose.yml
	echo "Docker compose is now in $INSTALLATION_DIR and can be seen above"

}

#docker run --rm  --privileged multiarch/qemu-user-static:register --reset

createDockerNetwork

prepareDockerCompose
