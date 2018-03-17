UserApplication
===============

Requirements
------------
1. Java 8+ (for Java Date and Time API)
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

The base url for the application is `/userapp/v1/`


TODOs
-----
1. Enable HTTPS


Future improvements
----------------------
1. Manage the JWT secret in a separate app (war), so that previously issued JWTs are honored after a main app restart. If HA is desired, use a distributed cache (e.g. Infinispan).