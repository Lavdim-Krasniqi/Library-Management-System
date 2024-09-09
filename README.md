<h1>Library Management System</h1>

## Overview
The **Library Management System** is a Spring Boot application designed to manage the relationships between books and authors. It leverages **MongoDB** as the database to handle large datasets efficiently. This system allows users to manage personal information, which is used to grant access to library resources. The core functionality revolves around managing the **Book** and **Author** entities, which are closely linked to represent the relationships between books and their respective authors.

## Features

### Author Api
- **Create New Author** , **Update Author** , **Delete Author** , **Get Details Of Author** and **Get List Of Authors**.

### Book Api
- **Create New Book** , **Update Book** , **Delete Book** , **Get Details Of Book** and **Get List Of Authors**.
- **Book** is dependent by **Author** so when adding a book we should be sure for existence of author

### User Api
- **Add User**, **Add Admin**, **Login**, **Revoke Token** 

### Security
  When **User** logs in, system generates an **Access Token** and a **Refresh Token**.
- **Access Token:** Is used for accessing application resources.
- **Refresh Token:**: Is used by system to generate new **Access Token** if **Refresh Token** is still valid.
- System provides ability to destroy token if user things that it is stolen.

### Auto Generated
By default system generates two users
- **User** with role **admin** with **username**: admin and **password**: admin
- **User** with role **user** with **username**: user and **password**: user

### Roles
- Admin
- User

### Role Privileges
- Admin has right to: **read, write, update, delete**
- User has right to: **read**



## Setup and Installation
### Prerequisites
- Java 21
- Maven
- Docker
- IntelliJ IDE

### Steps
1. **Clone the repository**:
   ```bash
   https://github.com/Lavdim-Krasniqi/Library-Management-System.git
2. **Use terminal for running commands**
3. **In terminal switch directory to cloned folder**
4. **Run Command for creating application docker image** 
   ```bash
   mvn spring-boot:build-image -Dspring-boot.build-image.imageName=api  -DskipTests=true
5. **Wait until **api** docker image is created**    
6. **Run below command for starting docker containers**
   ```bash
   docker-compose -f docker-compose.yml up -d
7. **Access Application using Swagger Ui in link:  http://localhost:8080/swagger-ui/index.html#/** 

## API Endpoints

For more details about endpoints after running the app based on previous description, visit this link http://localhost:8080/swagger-ui/index.html#/

## Integration and Unit Testing
- **For **Integration Testing** are used **TestContainers** for running **MongoDB** container**

## How to run tests
1. **Run maven commands:**
2. **mvn clean**
3. **mvn test**
4. **Before running these commands make sure that you have docker up and running**

## How to connect to MongoDB Compass
Use this url: **mongodb://admin:admin@localhost:27018/library-management-system**