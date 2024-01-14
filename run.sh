#!/bin/sh

nohup java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.Server -s  2>&1 &
nohup java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.Server 2>&1 &

java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.SocketClient  -p 27993 localhost alex
java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.SocketClient  -p 27994 -s localhost alex

