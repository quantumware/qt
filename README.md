README
======

## Build
mvn clean package

## Run
java -jar target/qt-1.0.0.jar server src/main/resources/config.yml

## Test
mvn test -DskipTests=false

## Browser / Postman
http://localhost:9080/qt/crawl?url=http://www.google.com&maxDepth=1