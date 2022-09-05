#!/bin/bash
# run jacoco report build
mvn -P coverage jacoco:report clean package
