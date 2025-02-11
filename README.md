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

liquibase dropAll
liquibase clearCheckSums
liquibase update --log-level debug/info

## Restrictions

- Code structure should follow best standards
- Reduce number of vouchers after successful applied while purchasing product
- Customer should have only one product at a time

- If customer wants to buy another product, preious product should be replaced by new one
- Customer cannot by prepaid and postpaid subscriptions together
- Customer can buy multiple prepaid
- While replacing prepaid to postpaid, all prepaid should be removed.
- At a time customer can buy only one prepaid

- Customer cannot buy multiple postpaid
- Customer can change postpaid only after validity expire
- If customer try to change postpaid subscription error should be thrown
   you can buy new sub only on DD-MM-YYYY
- Customer can upgrade postpaid plan with immediate effect
Valid error code and error code description should be thrown




Promocode applies only on mobile product
Promocode will not applyon subscription

[Related guide section...](https://quarkus.io/guides/hibernate-orm)


