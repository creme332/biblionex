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
  - [Functional Requirements](#functional-requirements)
    - [Authentication](#authentication)
    - [Checking out](#checking-out)
    - [Checking in](#checking-in)
    - [Renewal](#renewal)


## Introduction

### Purpose

The purpose of this document is to specify the software requirements for a library management system called BiblioNex. This document serves as a comprehensive guide outlining the functional and non-functional requirements necessary for the successful design, development, and deployment of the system.

### Product Scope

BiblioNex is a comprehensive library management system designed to streamline the management of library resources and enhance the overall user experience for both librarians and patrons. The system encompasses various modules and features aimed at efficiently managing books, users, loans, and providing reporting and analytics capabilities.

The system will support the management of only the following material types, including but not limited to:

- **Books**: Fiction, non-fiction, reference books, etc.
- **Journals**: Academic journals, periodicals, magazines, etc.
- **Videos**: DVDs, Blu-rays, streaming videos, etc.

While BiblioNex aims to provide a comprehensive library management solution, there are certain functionalities that fall outside the product scope. These include:

- Advanced cataloging features such as metadata enrichment and authority control.
- Integration with external systems such as academic databases or online bookstores.
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

1. **Librarians**: Librarians are administrators responsible for managing library operations. They have access to advanced functionalities such as adding, updating, and deleting materials from the library catalog, managing user accounts, tracking book loans, generating reports, and customizing system settings. Librarians are typically well-versed in library management practices and have a comprehensive understanding of the system's capabilities.
2. **Patrons**: Patrons are library users who interact with the system to browse the library catalog, search for items, borrow and return items, and manage their account details. Patrons may vary in their technical proficiency, with some being comfortable navigating the system independently, while others may require assistance from librarians.

### Product Functions

The system is designed to perform a range of major functions, providing users with
a seamless and enjoyable shopping experience. A high-level summary of the functionalities includes:

- **Material Management**: Add, update, and delete materials in the library catalog.
- **User Management**: Register and authenticate users, manage user profiles.
- **Loan Management**: Track book loans, handle renewals and returns.
- **Reporting and Analytics**: Generate reports on library operations and usage patterns.
- **Customization**: Configure system settings and loan policies.

## User Requirements

### Librarian

Librarians should be able to:

- Log into the system.
- Log out the system.
- Manage the library catalog by adding, updating, or deleting materials.
- Search the library catalog.
- Create new patron accounts.
- Search patron by email and name.
- Check in a material.
- Check out a material.
- Renew a material.
- Order new materials.
- Generate reports on library operations and usage patterns.

### Patron

Patrons should be able to:

- Log into the system.
- Log out the system.
- Browse the library catalog.
- Search for specific items using keywords, titles, authors, or categories.
- View material details and availability status.
- Borrow an available material.
- Renew an existing loan for a material.
- View their loan history and manage their account details.
- Change their account password.
- Change their account information.
- View their borrowing history.
- Pay outstanding fines.

## Functional Requirements

### Authentication

### Checking out

- When a patron wants to borrow a material, they will present their ID. A library staff member then enters the material ID into the library management system, associating it with the patron's account.
- The system records the due date by which the item must be returned.
- If patron ID or material ID does not exist in system, an appropriate error must be displayed.

### Checking in

- When a patron brings back an item, they hand it over to library staff, who then inputs the material ID into the system.
- The system updates the item's status to indicate that it has been returned, and the patron's account is updated accordingly, marking the item as no longer checked out to them.

### Renewal

- The system shall provide patrons with an option to renew eligible items through the user interface.
- Upon selecting the renewal option, the system shall:
  - Verify the eligibility of the selected item for renewal.
  - Update the due date of the item based on the library's renewal policy.
  - Generate a confirmation message indicating the successful renewal of the item.
  - Reflect the updated due date in the user's account and item status.