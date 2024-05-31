# Java Coding Standards

## Table of Contents
- [Java Coding Standards](#java-coding-standards)
  - [Table of Contents](#table-of-contents)
  - [General Principles](#general-principles)
  - [File Naming Conventions](#file-naming-conventions)
  - [Class and Interface Naming](#class-and-interface-naming)
  - [Method and Variable Naming](#method-and-variable-naming)
  - [Code Structure](#code-structure)
  - [Formatting](#formatting)
    - [Example:](#example)

## General Principles
- Write clean, readable, and maintainable code.
- Follow the SOLID principles.
- Adhere to the DRY (Don't Repeat Yourself) principle.
- Prefer composition over inheritance.
- Ensure that the code is well-documented and easy to understand.

## File Naming Conventions
- Source file names should be in camel case and match the public class name within the file. Example: `MyClass.java`
- Test file names should be the same as the class they are testing with the suffix `Test`. Example: `MyClassTest.java`

## Class and Interface Naming
- Class names should be nouns, written in UpperCamelCase. Example: `CustomerOrder`
- Interface names should be adjectives, also in UpperCamelCase. Example: `Runnable`, `Serializable`
- Avoid abbreviations and acronyms unless they are widely accepted. Example: `HttpRequest` instead of `HTTPReq`

## Method and Variable Naming
- Method names should be verbs, written in lowerCamelCase. Example: `calculateTotal()`
- Variable names should be written in lowerCamelCase. Example: `totalAmount`
- Constant names should be written in all uppercase with words separated by underscores. Example: `MAX_VALUE`

## Code Structure
- Each class should have its own file.
- Organize classes logically into packages. Example: `com.myapp.service`, `com.myapp.model`

## Formatting
- **Use 4 spaces for indentation. Do not use tabs.**
- Limit lines to 100 characters.
- Use blank lines to separate logical sections of the code.
- Place the opening brace `{` at the end of the line declaring the class, method, or block, and the closing brace `}` on a new line.

### Example:
```java
public class MyClass {
    private int myVariable;

    public MyClass(int myVariable) {
        this.myVariable = myVariable;
    }

    public int getMyVariable() {
        return myVariable;
    }

    public void setMyVariable(int myVariable) {
        this.myVariable = myVariable;
    }
}
```