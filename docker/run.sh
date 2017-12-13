#!/usr/bin/env sh

CLASSPATH=conf/
for i in lib/*.jar; do
    CLASSPATH=$CLASSPATH:$i
done

java -Duser.dir=/opt/app -cp $CLASSPATH play.core.server.ProdServerStart
