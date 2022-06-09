#!/bin/bash
docker build -t ntt/hextranslator .
docker create --name hextranslator -p 8081:8080 ntt/hextranslator
docker start hextranslator
