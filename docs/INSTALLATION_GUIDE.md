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
EMAIL_USERNAME=steamy.sip@gmail.com
EMAIL_PASSWORD=aoheaoedbuewhmnw
```
- `EMAIL_USERNAME` and `EMAIL_PASSWORD` are the credentials of the Gmail account from which emails will be sent when a patron resets his password. It is recommended to use a Gmail App password for `EMAIL_USERNAME` instead of the true gmail account password.
- Update the values assigned to `DB_USERNAME` and `DB_PASSWORD` with your MySQL login details.


Install Maven dependencies:

```bash
mvn clean install
```

## Database setup

Run the SQL script found in `db/biblionex.sql` to setup the database:

```bash
mysql -u root -p < db/biblionex.sql
```

