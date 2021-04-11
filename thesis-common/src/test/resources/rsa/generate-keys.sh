#!/usr/bin/env bash
echo "Rsa keys.."
openssl genrsa -out private_key.pem 2048
openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt
openssl rsa -in private_key.pem -pubout -outform DER -out public_key.der

openssl rsa -in private_key.pem -outform PEM -pubout -out public_key.pem


echo "keys generation completed!"
echo ${PWD}
ls -l