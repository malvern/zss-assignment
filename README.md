# zss-assignment
zss backend home test

#### How to Run this application

##### Install
Clone this github repo here
  
  ```https://github.com/malvern/zss-assignment.git```
  
*switch to developer branch*
  
 ##### Build application run
  
         mvn clean install
         
  
  ##### Running application
  
       mvn clean spring-boot:run
      
  application will be exposed on port 9101
  
  
  ##### Running Unit Test
  
       mvn clean test
 
  ##### Endpoints were documented using swagger
    
    endpoints are exposed on 
    
  ``` http://localhost:9101/swagger-ui.html```
  
  #### Given more time what can be done
  
  - Adding Docker containers(test container) so that all integrations tests can run.(Book and Category api integration tests have been disabled).
 
  - Finish Transacction integration.(integration test for PurchaseBook.(Only 1 integration test was done).
      - adding validators
      - add options to allow users to purchase particular book(purchaseRequest object)
      -  check if selected book is in stock
  
  - implement security using spring security.


  
  
  
 
 
 
