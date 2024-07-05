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

Create a `.env` file with the following contents and update the values assigned to `DB_USERNAME` and `DB_PASSWORD` with your MySQL login details:

```
DB_URL=jdbc:mysql://localhost:3306/biblionex
DB_USERNAME=root
DB_PASSWORD=
EMAIL_USERNAME=
EMAIL_PASSWORD=
```

Install Maven dependencies:

```bash
mvn clean install
```

## Database setup

Run the SQL script found in `db/biblionex.sql` to setup the database:

```bash
mysql -u root -p < db/biblionex.sql
```

## Mailing setup

It is possible to send actual password reset emails to users in biblionex. To use this feature you must enter values for `EMAIL_USERNAME` and `EMAIL_PASSWORD` in your `.env` file.
These are the credentials of the Gmail account from which emails will be sent when a patron resets his password. 

> [!IMPORTANT]
> It is recommended to use a Gmail App password for `EMAIL_PASSWORD` instead of the true gmail account password.

