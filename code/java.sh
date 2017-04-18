#!/bin/bash
java -Djava.library.path=/usr/local/lib -cp .:/usr/local/share/java/zmq.jar $1
