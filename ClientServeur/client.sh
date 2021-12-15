#!/bin/bash

PROJECTROOT=~/Partage/PRO3600/ClientServeur

export CLASSPATH=$PROJECTROOT/target/classes

java pro11.AppliClient $*
