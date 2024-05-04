# Software Requirements Specification

## For BiblioNex

Version 1.0

Table of Contents
=================
- [Software Requirements Specification](#software-requirements-specification)
  - [For BiblioNex](#for-biblionex)
- [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
    - [Purpose](#purpose)
    - [Product Scope](#product-scope)
    - [Document Conventions](#document-conventions)
  - [Overall Description](#overall-description)
    - [Product Perspective](#product-perspective)
    - [User Classes and Characteristics](#user-classes-and-characteristics)
    - [Product Functions](#product-functions)
  - [User Requirements](#user-requirements)
    - [Librarian](#librarian)
    - [Patron](#patron)
  - [Use Case Diagram](#use-case-diagram)
  - [UML Class Diagram](#uml-class-diagram)
  - [Non-functional Requirements](#non-functional-requirements)
    - [Security](#security)
  - [References](#references)


## Introduction

### Purpose

The purpose of this document is to specify the software requirements for a library management system called BiblioNex. This document serves as a comprehensive guide outlining the functional and non-functional requirements necessary for the successful design, development, and deployment of the system.

### Product Scope

BiblioNex is a comprehensive library management system designed to streamline the management of library resources and enhance the overall user experience for both librarians and patrons. The system encompasses various modules and features aimed at efficiently managing books, users, loans, and providing reporting and analytics capabilities.

While BiblioNex aims to provide a comprehensive library management solution, there are certain functionalities that fall outside the product scope. These include:

- Advanced cataloging features such as metadata enrichment and authority control.
- Integration with external systems such as academic databases or online bookstores.
- Physical inventory management beyond basic tracking of book availability.
- E-commerce capabilities for online book purchases or payments.

### Document Conventions

| Abbreviation | Definition                          |
| ------------ | ----------------------------------- |
| SRS          | Software Requirement Specifications |
| UML          | Unified Modelling Language          |

## Overall Description

### Product Perspective

The BiblioNex library management system is a standalone software application designed to operate within the context of a library environment. It serves as a centralized platform for librarians to manage library resources and for patrons to access and borrow books. While BiblioNex is an independent system, it can integrate with existing library infrastructure such as databases and authentication systems.

### User Classes and Characteristics

BiblioNex caters to two primary user classes:

1. **Librarians**: Librarians are administrators responsible for managing library operations. They have access to advanced functionalities such as adding, updating, and deleting books from the library catalog, managing user accounts, tracking book loans, generating reports, and customizing system settings. Librarians are typically well-versed in library management practices and have a comprehensive understanding of the system's capabilities.
2. **Patrons**: Patrons are library users who interact with the system to browse the library catalog, search for books, borrow and return items, and manage their account details. Patrons may vary in their technical proficiency, with some being comfortable navigating the system independently, while others may require assistance from librarians.
Both user classes may exhibit characteristics such as varying levels of computer literacy, preferences for user interface design, and specific needs or preferences regarding library services. BiblioNex aims to provide a user-friendly experience for both librarians and patrons, accommodating a diverse range of users and their requirements.

### Product Functions

The system is designed to perform a range of major functions, providing users with
a seamless and enjoyable shopping experience. A high-level summary of the functionalities includes:

- **Book Management**: Add, update, and delete books in the library catalog.
- **User Management**: Register and authenticate users, manage user profiles.
- **Loan Management**: Track book loans, handle renewals and returns.
- **Reporting and Analytics**: Generate reports on library operations and usage patterns.
- **Customization**: Configure system settings and loan policies.

## User Requirements

### Librarian

Librarians should be able to:

- Manage the library catalog by adding, updating, or deleting books.
- Register new users and manage user accounts, including authentication.
- Track book loans, handle renewals and returns.
- Generate reports on library operations and usage patterns.
- Customize system settings and loan policies.

### Patron

Patrons should be able to:

- Browse the library catalog to search for books.
- View book details and availability status.
- View their loan history and manage their account details.

## Use Case Diagram

![Use Case Diagram for regular users]()

![Use Case Diagram for administrators]()

## UML Class Diagram

![UML Class Diagram]()

## Non-functional Requirements

### Security

- Password Policy: The system shall enforce a password policy, including minimum length and
  complexity.
- Password Hashing: User passwords shall be securely hashed using industry-standard cryptographic hash functions such as bcrypt.
- User Authorization: Access to sensitive functionalities, such as order processing and administrative functions, shall be restricted based on user roles and permissions.

## References

1. Montoya J., SRS-Template, 2019. https://github.com/jam01/SRS-Template [online]. Accessed on 30 January 2024.