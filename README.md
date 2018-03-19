UserApplication
===============

Requirements
------------
1. Java 8 (for Java Date and Time API)
2. Wildfly 10
3. Local MongoDB instance running on the default port 27017 


Build
-----
1. `mvn package`


Deploy
------
1. Copy `target/userapp.war` to the Wildfly 10 `deployments` directory
2. While deploying the first time, the application will create a MongoDB database named `daniel_haeser_rech_userapp`
 

Using the application
---------------------

Default admin user login is `admin@foobar.com` and password is `123456`.  

The base url for the application is `/userapp/v1/`.  

A basic Swagger definition file is available at `/userapp/v1/swagger.json`

#### 1. Login ####

        POST http://localhost:8080/userapp/v1/login
        {
            "email": "admin@foobar.com",
            "password": "123456"
        }
The authentication token returns in the response body:

        {
            "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJwZXJtaXNzaW9ucyI6WyJVU0VSX0NSRUFURSIsIlVTRVJfUkVUUklFVkUiLCJVU0VSX1VQREFURSIsIlVTRVJfREVMRVRFIl0sImV4cCI6MTUyMTQxNjg2NH0.sIuy4X2c56DpbII13M2g-NeG0ig-tV_1m-_M0O1mF3LJgcNJIa8bccH54tIc34ZkceTMNdhHLAsNZuo1u4hbvA"
        }
For all subsequent requests, the token must be passed in the HTTP header:

        Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJwZXJtaXNzaW9ucyI6WyJVU0VSX0NSRUFURSIsIlVTRVJfUkVUUklFVkUiLCJVU0VSX1VQREFURSIsIlVTRVJfREVMRVRFIl0sImV4cCI6MTUyMTQxNjg2NH0.sIuy4X2c56DpbII13M2g-NeG0ig-tV_1m-_M0O1mF3LJgcNJIa8bccH54tIc34ZkceTMNdhHLAsNZuo1u4hbvA
The token expires after 10 minutes.         

#### 2. Create new user ####

        POST http://localhost:8080/userapp/v1/user
        Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJwZXJtaXNzaW9ucyI6WyJVU0VSX0NSRUFURSIsIlVTRVJfUkVUUklFVkUiLCJVU0VSX1VQREFURSIsIlVTRVJfREVMRVRFIl0sImV4cCI6MTUyMTQxNjg2NH0.sIuy4X2c56DpbII13M2g-NeG0ig-tV_1m-_M0O1mF3LJgcNJIa8bccH54tIc34ZkceTMNdhHLAsNZuo1u4hbvA
        {
            "name": "Daniel",
            "email": "daniel@foobar.com",
            "address": "Florianópolis",
            "phoneNumber": "(48) 99999 9999",
            "password": "123456"
        }
The response body returns a new user with the ObjectId

        {
            "id": "5aaefd077c683d1d2ccab4be",
            "name": "Daniel",
            "email": "daniel@foobar.com",
            "address": "Florianópolis",
            "phoneNumber": "(48) 99999 9999"
        }

#### 3. Retrieve the user ####

        GET http://localhost:8080/userapp/v1/user/5aaefd077c683d1d2ccab4be
        Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJwZXJtaXNzaW9ucyI6WyJVU0VSX0NSRUFURSIsIlVTRVJfUkVUUklFVkUiLCJVU0VSX1VQREFURSIsIlVTRVJfREVMRVRFIl0sImV4cCI6MTUyMTQxNjg2NH0.sIuy4X2c56DpbII13M2g-NeG0ig-tV_1m-_M0O1mF3LJgcNJIa8bccH54tIc34ZkceTMNdhHLAsNZuo1u4hbvA
The reponse body:
        
        {
            "id": "5aaefd077c683d1d2ccab4be",
            "name": "Daniel",
            "email": "daniel@foobar.com",
            "address": "Florianópolis",
            "phoneNumber": "(48) 99999 9999"
        }
        
#### 4. Update the user ####
Change the desired properties:

        PUT http://localhost:8080/userapp/v1/user
        Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJwZXJtaXNzaW9ucyI6WyJVU0VSX0NSRUFURSIsIlVTRVJfUkVUUklFVkUiLCJVU0VSX1VQREFURSIsIlVTRVJfREVMRVRFIl0sImV4cCI6MTUyMTQxNjg2NH0.sIuy4X2c56DpbII13M2g-NeG0ig-tV_1m-_M0O1mF3LJgcNJIa8bccH54tIc34ZkceTMNdhHLAsNZuo1u4hbvA
        {
            "id": "5aaefd077c683d1d2ccab4be",
            "name": "Daniel",
            "email": "daniel@foobar.org",
            "address": "Florianópolis, SC",
            "phoneNumber": "(48) 12345 12345",
            "password": "123456"
        }
        
#### 5. Delete the user ####        

        DELETE http://localhost:8080/userapp/v1/user/5aaefd077c683d1d2ccab4be
        Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJwZXJtaXNzaW9ucyI6WyJVU0VSX0NSRUFURSIsIlVTRVJfUkVUUklFVkUiLCJVU0VSX1VQREFURSIsIlVTRVJfREVMRVRFIl0sImV4cCI6MTUyMTQxNjg2NH0.sIuy4X2c56DpbII13M2g-NeG0ig-tV_1m-_M0O1mF3LJgcNJIa8bccH54tIc34ZkceTMNdhHLAsNZuo1u4hbvA

#### 6. Querying ####

Query for users named Daniel, skipping the first 20 results, max 10 results, ordered by name, then inverse-ordered by e-mail:

        GET http://localhost:8080/userapp/v1/user?filter=Daniel&offset=20&limit=10&orderby=name,-email 
        Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJwZXJtaXNzaW9ucyI6WyJVU0VSX0NSRUFURSIsIlVTRVJfUkVUUklFVkUiLCJVU0VSX1VQREFURSIsIlVTRVJfREVMRVRFIl0sImV4cCI6MTUyMTQxNjg2NH0.sIuy4X2c56DpbII13M2g-NeG0ig-tV_1m-_M0O1mF3LJgcNJIa8bccH54tIc34ZkceTMNdhHLAsNZuo1u4hbvA
You may omit any query param if the respective functionality is not desired.  

For the `filter` param you may use multiple terms separated by URL-encoded spaces:

        GET http://localhost:8080/userapp/v1/user?filter=Florianopolis%20York 
        Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJwZXJtaXNzaW9ucyI6WyJVU0VSX0NSRUFURSIsIlVTRVJfUkVUUklFVkUiLCJVU0VSX1VQREFURSIsIlVTRVJfREVMRVRFIl0sImV4cCI6MTUyMTQxNjg2NH0.sIuy4X2c56DpbII13M2g-NeG0ig-tV_1m-_M0O1mF3LJgcNJIa8bccH54tIc34ZkceTMNdhHLAsNZuo1u4hbvA
An exact phrase can be searched by enclosing it in single quotes:

        GET http://localhost:8080/userapp/v1/user?filter='New%20York'
        Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJwZXJtaXNzaW9ucyI6WyJVU0VSX0NSRUFURSIsIlVTRVJfUkVUUklFVkUiLCJVU0VSX1VQREFURSIsIlVTRVJfREVMRVRFIl0sImV4cCI6MTUyMTQxNjg2NH0.sIuy4X2c56DpbII13M2g-NeG0ig-tV_1m-_M0O1mF3LJgcNJIa8bccH54tIc34ZkceTMNdhHLAsNZuo1u4hbvA


TODOs
-----
1. Enable HTTPS
2. Add JWT renewal on successful authorization  
3. Add proper error messages for the web services


Future improvements
----------------------
1. Manage the JWT secret in a separate app (war), so that previously issued JWTs are honored after a main app restart. If HA is desired, use a distributed cache (e.g. Infinispan).
