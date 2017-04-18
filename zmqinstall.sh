#!/bin/bash

sudo apt-get install -y libtool pkg-config build-essential autoconf automake
sudo apt-get install -y git
sudo apt-get install libzmq-dev
#install libsodium
git clone git://github.com/jedisct1/libsodium.git
cd libsodium
./autogen.sh
./configure $$ make check
sudo make install
sudo ldconfig
#install zeromq
wget http://download.zeromq.org/zeromq-4.1.2.tar.gz
tar -xvf zeromq-4.1.2.tar.gz
cd zeromq-4.1.2
./autogen.sh
./configure && make check
sudo make install
sudo ldconfig

