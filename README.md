# Hub Central

This project provides a critical service for the TetraCube platform, responsible for issuing and managing access tokens 
that allow secure interactions within the ecosystem. The service ensures that only authorized users and applications 
can access various components of the platform.

In addition to handling token generation and verification, the service maintains essential knowledge about the house 
and its associated connected applications, ensuring that access permissions are appropriately aligned with the house's 
current setup and connected devices. This allows for seamless and secure operations across the platform,
from controlling smart home devices to interacting with telemetry data.

This service is a cornerstone of the TetraCube ecosystem, enabling secure and streamlined access to its various modules and services.

## Tech stack

This project uses Quarkus (the Supersonic Subatomic Java Framework) and Java 21 as programming language.
Combining the two technologies guarantee to the service a good performance on homelabs and low-powered hardware,
instead if you decide to deploy in the cloud, the combination guarantee the low infrastructure pricing.

The Hub central uses PostgreSQL as persistence database.

## Running the application 

Requirements:
* Fully functional PostreSQL database with user and database created
* `.env` file or environment variable set as declared in the template file `.env.template`

### Development mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/hub-central-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.
