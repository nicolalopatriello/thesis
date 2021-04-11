#!/usr/bin/env sh
#--------------------------
#
# Thesis Core Launcher
# 
#-------------------------

# Configuration

# Xmx specifies the maximum memory allocation pool for a Java virtual machine
Xmx=2048

# Xms specifies the initial memory allocation pool
Xms=256

JVM_OPTS="-Xmx${Xmx}m -Xms${Xms}m"

#-------------------------

source ${HOME}/.bashrc
cat ${THESIS_HOME}/thesis-core/bin/banner.txt
MAIN_CLASS=it.nicolalopatriello.thesis.core.CoreApplication

CFG_DIR=${THESIS_HOME}/thesis-core/cfg
LIB_DIR=${THESIS_HOME}/thesis-core/lib

export SPRING_CONFIG_LOCATION=${CFG_DIR}/application.properties

LOG4J_CONF=${CFG_DIR}/log4j.properties
CLASSPATH="$CFG_DIR/*:$LIB_DIR/*"


java -cp "$CLASSPATH" ${JVM_OPTS} -DlogDir=${THESIS_LOG_DIR}  -DlogFile=${THESIS_LOG_DIR}/thesis-core.log -Dlog4j.configuration=file:$LOG4J_CONF $MAIN_CLASS $ARGUMENTS > ${THESIS_LOG_DIR}/thesis-core.out
