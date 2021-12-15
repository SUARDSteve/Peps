#!/bin/bash

PROJECTROOT=~/Partage/PRO3600/ClientServeur

export CLASSPATH=$PROJECTROOT/target/classes

java tsp.pro11reseau.AppliClient $*
