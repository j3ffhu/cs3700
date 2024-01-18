#!/bin/sh

java -cp ./cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar hw1.SocketClient  $ARGS

#  -p 27993 proj1.3700.network john_dos
# git clone https://github.com/j3ffhu/cs3700.git
# docker build -t cs3700:hw1 . 
# docker run -it --env ARGS="-p 27993 proj1.3700.network jane_dos"  --rm cs3700:hw1
# docker run -it --env ARGS="-p 27994 -s proj1.3700.network jane_dos"  --rm cs3700:hw1
 
