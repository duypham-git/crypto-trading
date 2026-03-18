# Getting Started
### 1. How to run the application:
> Go to the root folder of the project
>
> Run command: `./gradlew bootRun`

### 2. API Documentation:
> * Postman: https://www.postman.com/duyphamtrandio/workspace/cryptotrading/request/63145-be89a3f8-4b80-4eff-b07f-83a3c5d4e328?action=share&creator=63145&ctx=documentation
> * APIs:
>   * GET /v1/crypto/wallet/balance
>     * Return the wallet balance of an userId
>     * Required requestHeader: userId
>   * GET /v1/crypto/price/latest
>     * Return the crypto latest price
>   * GET /v1/crypto/transactions
>     * Return the crypto transactions of an userId
>     * Required requestHeader: userId
>   * POST /v1/crypto/transfer
>     * Do the crypto transfer
>     * Required requestHeader: userId
>     * RequestBody:
> ```json
> {
>   
>    "requestId": "abc123",
>    "symbol": "ETHUSDT",
>    "side": "BUY",
>    "quantity": 0.5
> }
> ```
