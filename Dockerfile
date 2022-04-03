FROM openjdk:11
ADD target/ReadingIsGood-0.0.1-SNAPSHOT.jar ReadingIsGood.jar
ENTRYPOINT ["java","-jar","ReadingIsGood.jar"]