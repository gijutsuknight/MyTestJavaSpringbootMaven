# Project Creation

- https://start.spring.io/

# Environment

- java 17

# Development Tools
- Intellij (Build #IC-251.25410.129, built on May 9, 2025)

# Swagger Access
- http://localhost:8100/mytestjavaspringbootmaven/swagger-ui/index.html

# Project Branching

- main (The main for release, versioning tag here)
- develop/master (Development Should be done in here)
- develop/feature (Specific Function)
# Run Database
- MySQL
```
docker run --name my-local-mysql -v /Volumes/T7/DockerVolume/MyMySQLVolume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=password -d -p 3306:3306 mysql:lts

docker start my-local-mysql
```