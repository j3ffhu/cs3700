#!/bin/sh

# submit host  proj1.3700.network
java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.SocketClient  "$@"

# java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.SocketClient  $ARGS

#  -p 27993 proj1.3700.network john_dos
# docker build -t cs3700:hw1 . 
# docker run -it --env ARGS="-p 27993 proj1.3700.network jane_dos"  --rm cs3700:hw1
# docker run -it --env ARGS="-p 27994 -s proj1.3700.network jane_dos"  --rm cs3700:hw1
 
