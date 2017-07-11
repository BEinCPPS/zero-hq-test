# Zero-hour Quality Testing

Back End server for [Mobile application](https://github.com/BEinCPPS/zero-hq-test-mobile).

## Usage
### Development
`mvn` (default profile `dev`) the command creates and starts the default Spring Boot application server.
### Production
`mvn package -P prod` this command creates a war file to copy inside a servlet container (Apache Tomcat 7+)
### Docker image
 `mvn clean package docker:build` the docker image created is tagged as `beincpps/zero-hq-test:latest`
 