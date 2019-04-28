# Document Management System

**Application for managing documents based on templates.**

## Short Description

The project has three modules:
- Core
- Rest
- UI

Core module contains entities, business logic and data access layer.
Rest module is a web service.
It includes Core module and has controllers for serving incoming requests.
Also, the Core module can be used as a background layer in traditional web or
desktop application with or without graphical user interface.

The main technologies used in this project are:

- MySQL RDBMS
- Java (Hibernate ORM, Spring Framework)
- TypeScript (Angular)

Core and Rest modules are implemented using Spring Framework.
Hibernate is used in Core module for object-relational mapping.
UI module is an Angular single page application.

## Install and Run Project
For database layer, you need MySQL RDBMS.

For running backend part of the application,
you need Java 8 and some Java application server,
like Tomcat or Jetty. To install and build it, you need to have Maven 3.6+.

For running frontend part of the application, you need to have Angular CLI 7+.
For install appropriate libraries, you need to have Node Package Manager 5.5
or higher.

### Configure Database
Take a look at `/core/src/main/resources/database.sql` file and execute that SQL
script in your MySQL client app. If you don't have it, you can install
SQLyog Community Edition, it's free.

### Install and Run Web Service
Run `mvn clean install` command on the project path in order to fetch
all necessary Java libraries and build the web service. 
Copy `document-management-system.war` from rest module's `target` directory
to `webapps` folder in your application server. Run the application server.

Also, you can install, build and run the web service using embedded tools in your IDE.

### Install and Run Single Page Application
Run `npm install` command on the ui module path in order to fetch all necessary
JavaScript/TypeScript libraries.
Take a look at `/ui/src/environments/environment.ts` file, and set the `backendUrl`
property to the current web service URL. By default it is `http://localhost:8080`.
Run `ng serve --open` command to build and run the Angular project.

### Run Tests
Run `mvn test` command in order to run unit and integration tests for Core and
Rest modules.