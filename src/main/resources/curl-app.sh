#!/bin/bash

for ((; ; )) do
  echo ""
  date
  curl http://shutdownhook:8080/api/hello
  sleep 1
done
