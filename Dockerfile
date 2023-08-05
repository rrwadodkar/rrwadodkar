FROM openjdk:20
MAINTAINER rrwadodkar@gmail.com
COPY target/UserManagementMircroservice-0.0.1-SNAPSHOT.jar UserManagementMircroservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/UserManagementMircroservice-0.0.1-SNAPSHOT.jar"]