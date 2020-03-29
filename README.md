Sample projects for showing how to setup a basic OAuth2 protocol implementation with Spring Security using recently changes introduced by `spring boot 2.2.5`
******

> **Note**: As announced [here](https://spring.io/blog/2019/11/14/spring-security-oauth-2-0-roadmap-update), Spring Security team plains to discontinue `authorization-server`, maybe in future will be deprecated. You can found a discuss about this [here](https://github.com/spring-projects/spring-security/issues/6320#issuecomment-564151705).

For all samples, except the first one, you **need setup database in `application-properties`**, currently using MySQL.

### [OAuth2 Simple Configuration](https://github.com/lucasvufma/spring-oauth2-samples/tree/master/spring-oauth2-simple/demo)

Simpliest configuration to run the Authorization and Resource server with Client and User in-memory. You can find and set User, username and password in [SecurityConfig](https://github.com/lucasvufma/spring-oauth2-samples/blob/master/spring-oauth2-simple/demo/src/main/java/com/samples/oauth2/simple/demo/config/SecurityConfig.java) aswell for Client in [AuthorizationServerConfig](https://github.com/lucasvufma/spring-oauth2-samples/blob/master/spring-oauth2-simple/demo/src/main/java/com/samples/oauth2/simple/demo/config/AuthorizationServerConfig.java).

Flow:
1. With postman or your preffered tool, open a POST request to `localhost:8080/oauth/token`, choose `Basic Auth` and insert the `CLIENT_ID`, `CLIENT_SECRET` provided (default `client` and password `secret`), in body choose `form-data`, add a field `grant_type` and set it as `password` and create more two fields `username` and `password` givin it the value's setted (default `john` and `123`)

Using Curl
 ```bash 
 curl --location --request POST 'localhost:8080/oauth/token'
\--header 'Authorization: Basic Y2xpZW50OnNlY3JldA==' 
\--form 'username=john' 
\--form 'password=123' 
\--form 'grant_type=password' 
```
You should receive a response with the `acess_token`
```JSON
{
    "access_token": "4a90ff51-78c3-4457-a5bb-eb7b66f56125",
    "token_type": "bearer",
    "refresh_token": "9c498e89-70f8-469e-8236-b00bb1766aeb",
    "expires_in": 43199,
    "scope": "read"
}
```
2. You can now acess the protected resources by passing the folowed parameter `?acess_token=` with the received token. For check the token validity open a new GET request with the token to `localhost:8080/oauth/check_token?token=` and you should get all informations about it

Using Curl 
```bash  
curl --location --request POST 'localhost:8080/oauth/check_token?token=786819ad-9fcc-4ed3-8503-cc1eebfb0315' 
\--header 'Authorization: Basic Y2xpZW50OnNlY3JldA==' 
\--form 'username=john' \--form 'password=123' 
\--form 'grant_type=password'
```

You should receive a response with all informations about the specified token
```JSON
{
    "active": true,
    "exp": 1585560701,
    "user_name": "john",
    "authorities": [
        "ROLE_USER"
    ],
    "client_id": "client",
    "scope": [
        "read"
    ]
}
```
### [OAuth2 Example with In-Memory Authentication and Token Store](https://github.com/lucasvufma/spring-oauth2-samples/tree/master/spring-oauth2-inmemory/demo)

In-Memory Authentication of Client and Token Store. Similar to the previous example but use a in-memory token store and get user information in database,  each User has a Role relationed, tokens remains in dynamic memory so all token informations get lost when the server is restarted. 

Since now in this example user is retrivied from database you should set the User, username and password in [DataInitializr](https://github.com/lucasvufma/spring-oauth2-samples/blob/master/spring-oauth2-inmemory/demo/src/main/java/com/samples/spring/oauth2/inmemory/demo/config/Components/DataInitializr.java) (default is `lucasvufma@gmail.com` and `123` for `ROLE_CLIENT` and `cliente@gmail.com` and `123` for `ROLE_ADMIN`).

Flow is equal to the first example.

###  [OAuth2 Example with JDBC Token Store](https://github.com/lucasvufma/spring-oauth2-samples/tree/master/spring-oauth2-jdbc/demo)

Similar to the In-Memory example but uses JDBC store. Storing all tokens and also client informations, tokens remains in database, so all token informations don't get lost when the server is restarted, but since it's use JDBC store the speed isn't excelent for a `authorization-server`. If speed is a think to worry about you should use a non-relational database for store that tokens and they information.

For store token you need aditional table in your schema and they are provided and initialized in application start from [schema.sql](https://github.com/lucasvufma/spring-oauth2-samples/blob/master/spring-oauth2-jdbc/demo/src/main/resources/schema.sql), you can insert client by the followed statement that also is inserted by default from schema.sql
```SQL
INSERT INTO oauth_client_details (client_id, client_secret, web_server_redirect_uri,
  scope, access_token_validity, refresh_token_validity,
   resource_ids, authorized_grant_types,
    additional_information)
SELECT * FROM (SELECT 'client',
     '{bcrypt}$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu',
      'http://localhost:8080/user', 'READ,WRITE', '3600', '10000', 'client_front',
       'authorization_code,password,refresh_token,implicit', '{}') AS tmp
WHERE NOT EXISTS (
    SELECT client_id FROM oauth_client_details WHERE client_id = 'client'
) LIMIT 1;
```
realize that client password is encrypted in the statement and you should insert it with the same encryption that you used in your `@Bean` provided for `PasswordEncoder` and seted to be used in `configure` method for `AuthorizationServerConfig`, by default is a `BCrypt` instance.

Flow is equal to the previous and first example except that you need set the Client, username and password in `schema.sql` (was defined by me just for demonstration purpose, you can change the the way if you want, for example, to post it by a endpoint and so forth) and User is beign initialized in [DemoApplication](https://github.com/lucasvufma/spring-oauth2-samples/blob/master/spring-oauth2-jdbc/demo/src/main/java/com/samples/oauth2/jdbc/demo/DemoApplication.java).

