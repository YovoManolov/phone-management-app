# phone-management-app

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

## Packaging and running the application

The application can be packaged using:

```shell script
./gradlew build
```

```shell script
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

```shell script
./gradlew clean build 
./gradlew liquibaseUpdate -debug/-info


 How to check the local dependencies : 
 ./gradlew dependencies --configuration runtimeClasspath

```



### Liquibase commands

```
liquibase dropAll
liquibase clearCheckSums
liquibase update --log-level debug/info
```
## Restrictions
```
- Code structure should follow best standards - OK 
- Reduce number of vouchers after successful applied while purchasing product - OK check
- Customer should have only one product at a time - OK - check

- If customer wants to buy another product, previous product should be replaced by new one - OK check
- Customer cannot by prepaid and postpaid subscriptions together - OK check
- Customer can buy multiple prepaid - OK check
- While replacing prepaid to postpaid, all prepaid should be removed. - OK check
- At a time customer can buy only one prepaid - OK check
- customer-purchase join table - with info of purchase date and purchase prise which promotion code was applied.


- Customer cannot buy multiple postpaid - OK check

- Not implemented: Customer can change postpaid only after validity expire  
- Not implemented: If customer try to change postpaid subscription error should be thrown "you can buy new sub only on DD-MM-YYYY 
- Not implemented: Customer can upgrade postpaid plan with immediate effect 
- Valid error code and error code description should be thrown - OK - check


- OK check - Create customer should only create customer, not add product or purchase subscription
- Ok check - Purchase product API only meant for purchase product 
- Ok check - you should not send entire customer details in Purchase Product API
- Ok check While you purchase product include promotion vaucher also in the request.

- Promocode applies only on mobile product - ? 
- Promocode will not apply on subscription  - ? 
```

