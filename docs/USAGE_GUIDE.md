# Usage Guide

## Run project

Start your MySQL server:

```bash
sudo service mysql start
```

Run the application:

```bash
mvn exec:java -Dexec.mainClass=com.github.creme332.App
```

## Auto-login

To automatically login without going through registration or sign in, make the following modifications to `model/AppState.java`:

```java
    private UserType autoLogin = UserType.LIBRARIAN; // or UserType.PATRON to login as patron
```

## Run tests

```bash
mvn test
```

## Export database

To export the database using `mysqldump` (v10):

```bash
mysqldump -u root -p --databases  biblionex > db/biblionex.sql
```