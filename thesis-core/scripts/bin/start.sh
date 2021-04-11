#!/usr/bin/env sh
#--------------------------
#
# Thesis Core Launcher
# 
#-------------------------

cat banner.txt
MAIN_CLASS=it.nicolalopatriello.thesis.core.CoreApplication

CFG_DIR=../cfg
LIB_DIR=../lib

export SPRING_CONFIG_LOCATION=${CFG_DIR}/application.properties

LOG4J_CONF=${CFG_DIR}/log4j.properties
CLASSPATH="$CFG_DIR/*:$LIB_DIR/*"

PID_FILE="thesis-core.pid"

if [[ -f "${PID_FILE}" ]]; then
 echo "ERROR: ${PID_FILE} exists. Please check if there are already a running instance"
 exit 1
fi

nohup java -cp "$CLASSPATH" ${LS_JAVA_OPTS} -DlogDir=${THESIS_LOG_DIR}  -DlogFile=${THESIS_LOG_DIR}/thesis-core.log -Dlog4j.configuration=file:$LOG4J_CONF $MAIN_CLASS $ARGUMENTS > ${THESIS_LOG_DIR}/thesis-core.out  2>&1 &

echo $! > ${PID_FILE}

echo "Pid: $(cat ${PID_FILE})"


