FROM openjdk:8-jre-alpine

#COPY target/universal/com-elovirta-kuhnuri-cms-1.0-SNAPSHOT.zip /
#RUN unzip /com-elovirta-kuhnuri-cms-1.0-SNAPSHOT.zip -d /
#RUN rm /com-elovirta-kuhnuri-cms-1.0-SNAPSHOT.zip
#RUN chmod 755 /com-elovirta-kuhnuri-cms-1.0-SNAPSHOT/bin/com-elovirta-kuhnuri-cms
#WORKDIR /com-elovirta-kuhnuri-cms-1.0-SNAPSHOT
RUN mkdir -p /opt/app/conf
COPY target/universal/stage/conf /opt/app/conf
RUN mkdir -p /opt/app/lib
COPY target/universal/stage/lib /opt/app/lib
RUN mkdir -p /opt/workspace
WORKDIR /opt/app

EXPOSE 9000
VOLUME ["/opt/workspace"]

ENTRYPOINT ["java", "-Duser.dir=/com-elovirta-kuhnuri-cms-1.0-SNAPSHOT", "-cp", "conf/:lib/com-elovirta-kuhnuri-cms.com-elovirta-kuhnuri-cms-1.0-SNAPSHOT-sans-externalized.jar:lib/org.scala-lang.scala-library-2.11.8.jar:lib/com.typesafe.play.twirl-api_2.11-1.1.1.jar:lib/org.apache.commons.commons-lang3-3.4.jar:lib/org.scala-lang.modules.scala-xml_2.11-1.0.1.jar:lib/com.typesafe.play.play-server_2.11-2.5.13.jar:lib/com.typesafe.play.play_2.11-2.5.13.jar:lib/com.typesafe.play.build-link-2.5.13.jar:lib/com.typesafe.play.play-exceptions-2.5.13.jar:lib/com.typesafe.play.play-iteratees_2.11-2.5.13.jar:lib/org.scala-stm.scala-stm_2.11-0.7.jar:lib/com.typesafe.config-1.3.1.jar:lib/com.typesafe.play.play-json_2.11-2.5.13.jar:lib/com.typesafe.play.play-functional_2.11-2.5.13.jar:lib/com.typesafe.play.play-datacommons_2.11-2.5.13.jar:lib/joda-time.joda-time-2.9.6.jar:lib/org.joda.joda-convert-1.8.1.jar:lib/org.scala-lang.scala-reflect-2.11.8.jar:lib/com.fasterxml.jackson.core.jackson-core-2.7.8.jar:lib/com.fasterxml.jackson.core.jackson-annotations-2.7.8.jar:lib/com.fasterxml.jackson.core.jackson-databind-2.7.8.jar:lib/com.fasterxml.jackson.datatype.jackson-datatype-jdk8-2.7.8.jar:lib/com.fasterxml.jackson.datatype.jackson-datatype-jsr310-2.7.8.jar:lib/com.typesafe.play.play-netty-utils-2.5.13.jar:lib/org.slf4j.slf4j-api-1.7.21.jar:lib/org.slf4j.jul-to-slf4j-1.7.21.jar:lib/org.slf4j.jcl-over-slf4j-1.7.21.jar:lib/com.typesafe.play.play-streams_2.11-2.5.13.jar:lib/org.reactivestreams.reactive-streams-1.0.0.jar:lib/com.typesafe.akka.akka-stream_2.11-2.4.14.jar:lib/com.typesafe.akka.akka-actor_2.11-2.4.14.jar:lib/org.scala-lang.modules.scala-java8-compat_2.11-0.7.0.jar:lib/com.typesafe.ssl-config-core_2.11-0.2.1.jar:lib/org.scala-lang.modules.scala-parser-combinators_2.11-1.0.4.jar:lib/com.typesafe.akka.akka-slf4j_2.11-2.4.14.jar:lib/commons-codec.commons-codec-1.10.jar:lib/xerces.xercesImpl-2.11.0.jar:lib/xml-apis.xml-apis-1.4.01.jar:lib/javax.transaction.jta-1.1.jar:lib/com.google.inject.guice-4.0.jar:lib/javax.inject.javax.inject-1.jar:lib/aopalliance.aopalliance-1.0.jar:lib/com.google.inject.extensions.guice-assistedinject-4.0.jar:lib/com.typesafe.play.play-netty-server_2.11-2.5.13.jar:lib/com.typesafe.netty.netty-reactive-streams-http-1.0.8.jar:lib/com.typesafe.netty.netty-reactive-streams-1.0.8.jar:lib/com.typesafe.play.play-logback_2.11-2.5.13.jar:lib/ch.qos.logback.logback-classic-1.1.7.jar:lib/ch.qos.logback.logback-core-1.1.7.jar:lib/com.typesafe.play.play-jdbc_2.11-2.5.13.jar:lib/com.typesafe.play.play-jdbc-api_2.11-2.5.13.jar:lib/com.jolbox.bonecp-0.8.0.RELEASE.jar:lib/com.zaxxer.HikariCP-2.5.1.jar:lib/com.googlecode.usc.jdbcdslog-1.0.6.2.jar:lib/com.h2database.h2-1.4.192.jar:lib/tyrex.tyrex-1.0.1.jar:lib/com.typesafe.play.play-cache_2.11-2.5.13.jar:lib/net.sf.ehcache.ehcache-core-2.6.11.jar:lib/com.typesafe.play.play-ws_2.11-2.5.13.jar:lib/com.google.guava.guava-19.0.jar:lib/org.asynchttpclient.async-http-client-2.0.24.jar:lib/org.asynchttpclient.async-http-client-netty-utils-2.0.24.jar:lib/io.netty.netty-buffer-4.0.42.Final.jar:lib/io.netty.netty-common-4.0.42.Final.jar:lib/io.netty.netty-codec-http-4.0.42.Final.jar:lib/io.netty.netty-codec-4.0.42.Final.jar:lib/io.netty.netty-transport-4.0.42.Final.jar:lib/io.netty.netty-handler-4.0.42.Final.jar:lib/io.netty.netty-transport-native-epoll-4.0.42.Final-linux-x86_64.jar:lib/org.asynchttpclient.netty-resolver-dns-2.0.24.jar:lib/org.asynchttpclient.netty-resolver-2.0.24.jar:lib/org.asynchttpclient.netty-codec-dns-2.0.24.jar:lib/org.javassist.javassist-3.21.0-GA.jar:lib/oauth.signpost.signpost-core-1.2.1.2.jar:lib/oauth.signpost.signpost-commonshttp4-1.2.1.2.jar:lib/org.apache.httpcomponents.httpcore-4.4.4.jar:lib/org.apache.httpcomponents.httpclient-4.5.2.jar:lib/commons-logging.commons-logging-1.2.jar:lib/com.typesafe.play.filters-helpers_2.11-2.5.13.jar:lib/org.postgresql.postgresql-9.4-1201-jdbc41.jar:lib/org.jooq.jooq-3.9.1.jar:lib/org.jooq.jooq-codegen-3.9.1.jar:lib/org.jooq.jooq-meta-3.9.1.jar:lib/org.apache.maven.maven-plugin-api-2.2.1.jar:lib/org.apache.maven.maven-project-2.2.1.jar:lib/org.apache.maven.maven-settings-2.2.1.jar:lib/org.apache.maven.maven-model-2.2.1.jar:lib/org.codehaus.plexus.plexus-utils-1.5.15.jar:lib/org.codehaus.plexus.plexus-interpolation-1.11.jar:lib/org.codehaus.plexus.plexus-container-default-1.0-alpha-9-stable-1.jar:lib/junit.junit-3.8.1.jar:lib/classworlds.classworlds-1.1-alpha-2.jar:lib/org.apache.maven.maven-profile-2.2.1.jar:lib/org.apache.maven.maven-artifact-manager-2.2.1.jar:lib/org.apache.maven.maven-repository-metadata-2.2.1.jar:lib/org.apache.maven.maven-artifact-2.2.1.jar:lib/org.apache.maven.wagon.wagon-provider-api-1.0-beta-6.jar:lib/backport-util-concurrent.backport-util-concurrent-3.1.jar:lib/org.apache.maven.maven-plugin-registry-2.2.1.jar:lib/com-elovirta-kuhnuri-cms.com-elovirta-kuhnuri-cms-1.0-SNAPSHOT-assets.jar", "play.core.server.ProdServerStart"]
