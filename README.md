# Payment System 
## Practical Task by Georgi Kavalov

### Local development
#### Prerequisites:
 - JDK 21
 - Maven 3.9.6 (maven wrapper is provided for your convenience)
 - PostgreSQL 16.2
 - Docker 26.0.0

Project is buildable and runnable:
```bash
mvnw clean package -Pdev
mvnw spring-boot:run
```

### Docker
The solution is dockerised and can be run (along with the required database):
If you want to start the application in a docker environment build it using the default profile (omitting the -Pdev option)
and run the multi container docker environment 
```bash
mvnw clean package
docker-compose up
```

### Tests
Test coverage is at 80% of code lines.

### UI
Unfortunately a web portal UI is not implemented.
However, I have provided simple Swagger documentation accessible at: 
<a href="http://localhost:8080/v1/swagger-ui/index.html">SwaggerUI</a>

### User Guide
Here are some sample use cases.
Using the Swagger UI is easy enough, but you can take the payload and use any client you like

#### Create a Merchant
- request
  - path: /v1/merchants
  - type: POST
  - headers:
    - Content-type: application/json
```json
{
    "name": "MerchantOne",
    "description": "First Merchant",
    "email": "merchant@one.mail",
    "status": "ACTIVE",
    "totalTransactionSum": 0
}
```
- response
  - status: 201
  - headers:
    - location: {id}

#### Import merchants in bulk
Alternatively you can import many merchants from a file.
There are a couple of test files that can be tested with in the `test/resources` folder
If you supply a big enough file you run the risk of resource exhaustion.
You can configure the application by setting the `payment.system.csv.linesToProcess` so the backend 
can cope with large amounts of data.
- request
  - path: /v1/merchants/import
  - type: POST
  - headers:
    - Content-Type: multipart/form-data
  - form-data: file=@merchants.csv;type=text/csv
- response
  - headers:
    - Content-Type: multipart/form-data
``` 
2 merchants were imported
```
#### Get a merchant
- request
  - path: /v1/merchant/{id}
  - type: GET
- response
```json
{
    "name": "MerchantOne",
    "description": "First Merchant",
    "email": "merchant@one.mail",
    "status": "ACTIVE",
    "totalTransactionSum": 0,
    "transactions": []
}
```

#### Authorise a transaction for the merchant
- request
  - path: /v1/merchants/{id}/transactions 
  - type: POST
    - headers:
        - Content-type: application/json
```json
{
    "status": "APPROVED",
    "customerEmail": "customer@one.mail",
    "customerPhone": "0123456789",
    "referenceId": "customer_1_transaction_1",
    "customerAmount": 1
}
```
- response
    - status: 201
    - headers:
        - location: /transactions/{uuid}

#### Find the transaction 
- request
    - path: /v1/transactions/{uuid}
    - type: GET
- response
```json
{
    "timestamp": "2024-05-04T19:40:25.764115Z",
    "status": "APPROVED",
    "customerEmail": "customer@one.mail",
    "customerPhone": "0123456789",
    "referenceId": "customer_1_transaction_1",
    "merchant": {
        "name": "MerchantOne",
        "description": "First Merchant",
        "email": "merchant@one.mail",
        "status": "ACTIVE",
        "totalTransactionSum": 0
    },
    "customerAmount": 1
}
```
```
Note total transaction sum is not updated as the transaction is not paid for
```

#### Pay for the authorised transaction
- request
    - path: /v1/transactions/{uuid}
    - type: POST
        - headers:
            - Content-type: application/json
```json
{
  "status": "APPROVED",
  "customerEmail": "customer@one.mail",
  "customerPhone": "0123456789",
  "referenceId": "customer_1_transaction_payment_1",
  "approvedAmount": 1
}
```
- response
  - status: 201
  - headers:
    - location: {uuid}

```
Execute get merchant request again to inspect the total transaction sum. 
It must be the equal to the amount in the paid transaction.
```


#### Refund a paid transaction
- request
  - path: /v1/transactions/{uuid}
  - type: POST
    - headers:
      - Content-type: application/json
```json
{
  "status": "APPROVED",
  "customerEmail": "customer@one.mail",
  "customerPhone": "0123456789",
  "referenceId": "customer_1_transaction_refund_1",
  "reversedAmount": 1
}
```
- response
  - status: 201
  - headers:
    - location: {uuid}
```
Merchant's total transaction sum will be reverted back to the original sum
```
#### Delete a merchant

- request
  - path: /v1/transactions/{id}
  - type: DELETE
- response
  - status: 400
```
Merchant 1 has 3 transactions and cannot be deleted
```

Merchants cannot be deleted until all of their associated transactions are removed

#### Configure the transaction cleaner

A configuration property `payment.system.transactions.cleaner.timeLimitInMins` 
can be adjusted to delete transactions that are older than the specified limit.
Consider setting to a lower number to observe desired behaviour. Transactions 
can be checked via the following endpoint:

- request
  - path: /v1/transactions
  - type: GET
  - headers:
    - Content-type: application/json

### Future maintenance
There are various places which I have marked with `// TODO` comments identifying what is missing from the functionality.
Apart from them the following need to be added:
 - some UI web portal allowing user-friendly access to the information
 - application security protocol allowing role based users to log on and execute certain operations applicable to their privileges
 - general improvements to the model and the controller responses
 - additional tests covering erroneous use cases
