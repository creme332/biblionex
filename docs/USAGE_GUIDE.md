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

## Run tests

```bash
mvn test
```

## Export database

To export the database using `mysqldump` (v10):

```bash
mysqldump -u root -p --databases  biblionex > db/biblionex.sql
```