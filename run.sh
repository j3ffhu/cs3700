#!/bin/sh

if [ ! -z "${MOCK}" ]; then
  nohup java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.Server -s  2>&1 &
  nohup java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.Server 2>&1 &
fi


# submit host  proj1.3700.network
java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.SocketClient  $ARGS
java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.SocketClient  $ARGS_TLS

 
