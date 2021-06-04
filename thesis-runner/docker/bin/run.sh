#!/usr/bin/env sh

echo "starting"
MAIN_CLASS=it.nicolalopatriello.thesis.runner.RunnerApplication
HOME_DIR=/opt
CFG_DIR=$HOME_DIR/cfg
LIB_DIR=$HOME_DIR/lib
PROFILE="production"
ARGUMENTS="--spring.config.location=file:$CFG_DIR/ --spring.profiles.active=$PROFILE"


LOG4J_CONF=$CFG_DIR/log4j.properties
CLASSPATH="$LIB_DIR/*:$CFG_DIR/*"

java  -cp "$CLASSPATH" -Dlog4j.configuration=file:$LOG4J_CONF  $MAIN_CLASS $ARGUMENTS