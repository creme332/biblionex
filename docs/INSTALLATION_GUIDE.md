# Installation Guide

## Prerequisites

- Git
- Apache Maven
- Java JDK (v17 preferred)
- MySQL

## Project setup

Clone the project:

```bash
git clone git@github.com:creme332/biblionex.git
```

Navigate to the root directory of the project:

```bash
cd biblionex
```

Create a `.env` file with the following contents:

```
DB_URL=jdbc:mysql://localhost:3306/biblionex
DB_USERNAME=root
DB_PASSWORD=aaa
```

Update the values assigned to `DB_USERNAME` and `DB_PASSWORD` with your MySQL login details.


Install Maven dependencies:

```bash
mvn clean install
```

## Database setup

Run the SQL script found in `db/biblionex.sql` to setup the database:

```bash
mysql -u root -p < db/biblionex.sql
```

