#!/usr/bin/env sh

CLASSPATH=conf/
for i in lib/*.jar; do
    CLASSPATH=$CLASSPATH:$i
done

if [ -f /opt/app/RUNNING_PID ]; then
    rm /opt/app/RUNNING_PID
fi
java -Duser.dir=/opt/app -cp $CLASSPATH play.core.server.ProdServerStart
