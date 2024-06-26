#### BookStore Application
- It allows users to signup,signin,rent and return a Book

## Features
 - CRUD operations for USER,Book 
 - signup and signin of users using jwt token
 - Registration of user into bookstore
 - Authentication and Authorization of user
 - Renting and returning a Book

## Endpoints
- POST api/public/signup - create a user 
- POST api/public/signin - allows a user to login and generate a token JWt which can be used to   access the private Routes
- GET api/private/role/ - lists the roles in the database
- POST api/private/role/ - create a role 
- POST api/private/addrole - allows assigining a ADMIN role to a user by the user who is already having ADMIN role
- POST api/private/books/ - allows adding a book by the user who is having ADMIN role
- POST api/private/books/{bookId}/rent - allows a user to rent a available Book
- POST api/private/books/{bookId}/return - allows a user to return a rented Book

## Postman Collection
- [Download Postman Collection](/postman_collection)
