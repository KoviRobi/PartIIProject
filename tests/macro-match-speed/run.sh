#!/bin/sh

javac -cp .:../../target/part-II-project-1.0-SNAPSHOT-jar-with-dependencies.jar test.java
java -cp .:../../target/part-II-project-1.0-SNAPSHOT-jar-with-dependencies.jar test > /tmp/ram/results.log
cp /tmp/ram/results.log results.log
