# jwt-token-api

A Spring Boot application that provides secure user authentication with a jwt token.

Authentication works according to the usual jwt scheme: in jwtAuthFilter, the explicitness of the token is checked, if the user is not found, then he is written to the database and a token is generated for him. Upon subsequent authentication, a new token will be generated for it, and the previous one will expire. Upon successful validation, the SecurityContextHolder is updated and the user is granted access to the main controller behind the token.

For any incorrect actions, the code 403 will be thrown, which means access is denied.

All types of tokens have an expiration date after which they become unavailable. Requires generation of a new token.

All details about the user and his role are stored in the database, the user table. Passwords are cached using BCryptPasswordEncoder.

The generated sign in Key is used for signing in.
