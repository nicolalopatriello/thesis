#!/bin/bash

PID_FILE="thesis-core.pid"

if [[ -f "${PID_FILE}" ]]; then
 kill -9 $(cat ${PID_FILE})
 rm ${PID_FILE}
fi
 
