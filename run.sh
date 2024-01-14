#!/bin/sh

#nohup java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.Server -s  2>&1 &
#nohup java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.Server 2>&1 &

#comment when connect to class host
nohup bash -c "java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.Server -s  2>&1 &" && sleep 1
nohup bash -c "java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.Server 2>&1 &" && sleep 1

# submit host  proj1.3700.network
java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.SocketClient  $ARGS
java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.SocketClient  $ARGS

 
