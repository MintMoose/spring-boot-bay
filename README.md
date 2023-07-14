# Java Spring Boot Ecommerce React Website

## Summary
This project is a full-stack application built with Spring Boot and React. It features a secure user authentication system implemented with Spring Security and JWT (JSON Web Tokens). User information, addresses, and orders are stored in a PostgreSQL database using docker.

The frontend, developed with React, provides a user-friendly interface for browsing and purchasing products. It leverages the Stripe API to handle secure payment transactions. The backend, powered by Spring Boot, integrates with the Stripe API and manages the creation of checkout sessions.

The application ensures proper CORS (Cross-Origin Resource Sharing) configuration to allow requests from specified origins, providing a secure and seamless user experience.

Please note that this project utilizes HTTP instead of HTTPS for simplicity in a local development environment.

The current implementation also stores sensitive configuration keys, such as API keys and database credentials, directly in the application's configuration files, such as application.yaml and application.properties. While this approach is suitable for local development and learning purposes, it is not recommended for production environments.

In a production environment, it is highly recommended to employ a configuration management system or a cloud-based service, such as AWS Secrets Manager or Azure Key Vault, to securely store and manage credentials. 

## Table of contents

- [Overview](#overview)
  - [Screenshot](#project-screenshots)
  - [Links](#links)
  - [What I learnt](#what-i-learnt)
  - [Continued development](#continued-development)


## Overview

### Project Screenshots
<span>
</span>

### Links
- Live Site URL: 

## Usage
Make sure you have Node.js and npm installed. In the project directory, run the following commands:

### `npm install`
This command downloads the required package dependencies to run the project locally.

### `npm start`
Runs the app in development mode. Open http://localhost:3000 to view it in your browser.

The page will automatically reload when you make changes. Any lint errors will be displayed in the console.

## What I Learnt

Developing my first CRUD Spring Boot application has been a valuable learning experience. Through this project, I gained a deeper understanding of the Spring Boot framework, RESTful API development, and the fundamentals of database management. It allowed me to grasp the concepts of user authentication, JWT tokens, and integrating third-party APIs.

There were instances where the focus was on getting things working rather than finding the optimal solutions. As a result, the codebase may not be as clean and organized as i would have desired.

This experience has underscored the significance of upholding clean and well-organized code.
The importance of prioritizing writing clean, modular, and maintainable code. I understand that investing time and effort in creating an organized codebase pays off in the long run, benefiting not only myself but also for the entire development cycle/team.

Despite these challenges, the current project showcases the ability to deliver functional solutions within given constraints. It serves as a valuable learning experience and a foundation for future improvements, demonstrating the determination and adaptability required in software development.

## Continued development

Moving forward, the need to address weak input validation is apparent in order to enhance the overall reliability and security of the application. Validation will be a focal point, alongside other important aspects of development, to ensure a more robust and secure system.

In addition to strengthening validation logic, a focus on implementing continuous integration and continuous delivery (CI/CD) practices will be pursued. This approach will streamline testing and deployment processes, allowing for more efficient and reliable software development.

Optimizing the user experience and design will also be a key priority. By refining the user interface (UI), improving navigation, and enhancing the overall user flow, the application will deliver an intuitive and visually appealing experience.

Security concerns will remain at the forefront, with the implementation of secure authentication and authorization mechanisms, as well as measures to protect sensitive information and address potential vulnerabilities.

Considering the future direction of the application, exploring microservices architecture may be considered to leverage the benefits of scalability and maintainability.
