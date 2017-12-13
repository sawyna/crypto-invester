
**To build**: mvn clean install<br/>
**To Run**: mvn tomcat7:run -Dhttp.port={port}

If http.port is not specified, the port defaults to 8080

logs can be found in logging/ directory under root

**API:** 

GET request
`http://localhost:{port}/crypto-invester/api/investment/transactions/date/{date}/currency/{currency}/quantity/{quantity}`

date: Has to be in yyyy-MM-dd format and in the past <br />
currency: the three letter code of the currency <br />
quantity: Can be a double value but only precision till 4 digits is considered

Example:

<a>http://localhost:8080/crypto-invester/api/investment/transactions/date/2017-10-30/currency/BTC/quantity/1</a>
