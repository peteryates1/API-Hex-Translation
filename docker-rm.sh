#!/bin/bash
docker stop hextranslator
docker container rm hextranslator
docker image rm ntt/hextranslator:latest
