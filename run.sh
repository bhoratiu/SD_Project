#!/bin/bash

cd /opt/app

exec java -Xmx6G \
	-Dreactor.netty.http.server.accessLogEnabled=true \
	-jar \
	-XX:MaxRAMPercentage=50.0 \
	-XX:+UseContainerSupport \
	-XX:+HeapDumpOnOutOfMemoryError \
	-XX:+UnlockExperimentalVMOptions \
	-XX:+UseG1GC \
	-XX:MaxGCPauseMillis=60000 \
	-XshowSettings:vm \
	-Dlogging.level.ROOT=${LOG_LEVEL} \
	-Dspring.profiles.active=${SPRING_ACTIVE_PROFILE} \
		electrica_users.jar