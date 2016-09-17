# Description

This is a university course task. The aim is to learn JDBC

# Task

Make queries using JDBC to a DB that describes vanacies and
companies. The program should include CRUD operations as well as
some filtering queries. Testing need to be automated

# Implementation details

- Gradle project with maven dependencies

    build.gradle  - gradle configuration
    gradle wrapper - create a local cross-platform gradle distribution
    We will use h2 database for testing

- Packages

    + domain - domain objects
    + persistence.dao - DAO objects for accessing database
    + persistence - utility classes for working with database
    + test - test classes
    
- Domain objects
```
+------------+   +--------------------+
|   Company  |   |Vacancy             |
+------------+   +--------------------+
|id: long    |   |id: long            |
|name: String|   |position: String    |
+------------+   |estSalary: double   |
                 |company: Company    |
                 |requirements: String|
                 +--------------------+
```

- Entry point

    Application.java
    
- Database manipulation

    All the template code encapsulated inside PersistenceUtils.
    This class also takes care of error handling

