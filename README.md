# Classified ads (Baraholka)

Classified ads for any kind of advert (only back-end) with full soft delete for ads.

Technology:
- Spring Security
- Spring Data
- RESTful web service 
- Flyway (for sql)
- Swagger
- Microservice Architecture 
- Maven


To start app:
 1. package both module 
 2. rut them by: $ java -jar app_name.jar
 3. get token by $ curl -X POST -vu web:web http://localhost:8000/oauth/token  -H "Accept: application/json" -d "grant_type=password&username=admin@kramar.com&password=12345"
 4. call REST with access_toke like:
    
    http://localhost:8080/adverts
    
    GET /adverts HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json
    Authorization: Bearer 3ef68ea7-77a7-446e-bbdd-8d26ea252c81
    Cache-Control: no-cache
    Postman-Token: 362e61b8-2bbe-7de1-4d88-8a97a63e28e2

 5. all rest method you can find on swagger:
    http://localhost:8080/swagger-ui.html








