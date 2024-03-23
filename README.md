# Spring Boot Microservice

This project is inspired by the idea to quickly create a production ready project with all the required infrastructure at low cost yet with important security measures in place and ability to quickly scale in order to ship a quality product to early adopters. Ideal for quickly starting an app to validate ideas and scale if needed. The implementation includes code samples for different features. It uses Spring Boot as the framework of choice because it provides a nice set of convenience features when bootstrapping and plugging together the application. Spring Boot has taken Spring framework to the next level, by drastically reducing the configuration and setup time required for Spring projects. 

### 🌀 Prerequisites:
By default, the project uses JDK 11, but will also work with JDK 12 and above.

* **JDK**
  - Oracle
    - Java : http://www.oracle.com/technetwork/java/javase/downloads/index.html
  - Adoptium
    - Java : https://adoptium.net/temurin/releases
 
  - RedHat
    - Java : https://developers.redhat.com/products/openjdk/download
* [Maven](https://maven.apache.org/)
---------------
### 🌀 Build and run

Build
---------------
* Get the latest version from the git repository.
* Run ` mvn clean install` to build the project.


Run
---------------
#### 1. Using IntelliJ
Start the Server using  " Run `Server.main()` " command, selected from the dialog after right click on:
- Main Application class: `com.server.Server`

#### 2. Using CLI
To run the application from command line do following steps
- open `cmd` window
- change directory to the root of your microservice project
- run `mvn clean install` to create a jar-file of your microservice.
- call `java -jar ing-api/target/uber-ing-api-1.0.0-SNAPSHOT` from the console

Visit `localhost:8080/apidoc` to see the endpoints.

---------------
### 🌀 DB Migration
- change directory to 'db-migration'
- run
`mvn compile flyway:baseline; `
`mvn compile flyway:migrate; `

### 🌀 Developer Setup
#### Enable lombok

- https://projectlombok.org/setup/intellij
- Verify that annotation processing is enabled in Intellij (`File` -> `Settings` -> `Build, Execution, and Deployment`
  -> `Compiler` -> `Annotation Processers`)

### 🌀 WHAT TO TEST

- after the project is running and the flyway scripts ran, visit swagger - `localhost:8080/apidoc` to see the endpoints.
- you can create an user using the Post `v1/user` endpoint in the `ING API` definition. It will have the visitor role
- for the login part, select the `Authentication API` definition, and run the `/token` endpoint
- you will receive an access token `ey....`; the content can be seen on `jwt.io`
- use it to authorize the endpoints (click on authorize and write `Bearer ey...` - paste the whole token in place of `ey...`) ; example :`eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJJTkcgU2VydmljZXMiLCJuYmYiOjE3MTEyMzc1NTAsInBlcm1pc3Npb25zIjpbImRlbGV0ZTpwcm9kdWN0Iiwidmlldzpwcm9kdWN0IiwiY3JlYXRlOnByb2R1Y3QiLCJyZWFkOnVzZXIiXSwicm9sZXMiOiJ2aXNpdG9yIiwiaXNzIjoiSU5HIEF1dGggU2VydmljZSIsImFjdGl2ZSI6ImZhbHNlIiwiZXhwIjoxNzEzODI5NTUwLCJpYXQiOjE3MTEyMzc1NTAsImp0aSI6IlpUNEdEcnJJWnh4YVVVUm1mRUpXOCIsImVtYWlsIjoidmFsZW50aW4uY2lvYmFudTk4QGdtYWlsLmNvbSIsImlkZW50aXR5UHJvdmlkZXIiOiJpbmcifQ.Oo38OzE4_vdP_P_pPFv2PX6UhNVG5A2a9O2HUqb0dSj0uANgLy995wx18Ne_0v0P32EECVvut4EkIf13NlZ6W0LakTon-dtzD1uB-1OtPB4NJadvwea6zNDfk5glFOPUs5m5mOpfM7Bl-uNXSjGhnxhq2zuMuNv7z_ztGnIS90COq4B9sniPocnoUMH1I6eskBUYGHtP6H9OOuASuGVDWRJQHojNVjgKrsEi2WcvLnnz-BLOlaIFUg_RSxYDEQryfuewxKAHsN7GWAHJ396uJKEHxR_0eiikRrR05L5y-q-NVDyNEn-DlBgTTFmqUj_cy99ptdDJz4hTBF-le7b40w`
- in the begining you have no permissions, only for Get `/v1/user` load user endpoint
- authenticate with the admin account to use the permissions operations
- {
  "email": "admin@ing.com",
  "password": "test"
  }
- you can check this also on `jwt.io` , it has the administrator role; assign permissions to visitor or administrator role or directly to an user; 
- edit_product
  view_product
  create_product
  delete_product
are the keys of the permissions; use the load endpoints to check for the userKey of your newly created user so you can assign to the account permissions directly or you can assign to the visitor role ( key : `visitor_role_key`)
- authenticate again and check the new access token getting the new assigned permissions
- assign all the permissions to use all the Product endpoints

### 🌀 WHAT TO CHECK
-MockUtil class has a static mock example
-Product search uses criteria api for better performance, building the json directly from the database instead of a new iterations to map to json;this way if it had relations in it, it wouldn t have the n+1 select problem
-permissions and roles on the access token
-the database is created using flywayscript, making it realy easy to move to a new environment
-the error messages are internationalized, having the translations into message bundle
-when you create a new account, you will receive an email generated with thymeleaf, internationalized again

### 🌀 Usefull commands
    `mvn dependency:tree`
 
    `mvn dependency:tree -Dincludes=net.minidev:json-smart` //Filter the dependency tree
