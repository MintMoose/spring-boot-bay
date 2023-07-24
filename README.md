# Java Spring Boot Ecommerce React Website

## Summary
This project is a full-stack application built with Spring Boot and React. It features a secure user authentication system implemented with Spring Security and JWT (JSON Web Tokens). User information, addresses, and orders are stored in a PostgreSQL database using docker.

The frontend, developed with React, provides a user-friendly interface for browsing and purchasing products. It leverages the Stripe API to handle secure payment transactions. The backend, powered by Spring Boot, integrates with the Stripe API and manages the creation of checkout sessions.


## Table of contents

  - [Screenshot](#screenshots)
  - [Planning and Testing](#planning-and-testing)
  - [Technologies Used](#technologies-used)
  - [Challenges Faced](#challenges-faced)
  - [Key Learnings](#key-learnings)
  - [Continued development](#continued-development)

## Screenshots
<span>
  <img src=images/homepage-loggedIn.png height=400/>
  <img src=images/product-page-mobile.jpg height=400/>
    <img src=images/CheckoutPageAll.jpg height=300/>
  <img src=images/my-products.jpg height=300/>
<img src=images/erd_psql_db.png height=300/>
<img src=images/productPageBoth.jpg height=300/>
  <img src=images/products-list.jpg height=300/>
  <img src=images/spring-event-combined.png height=300/>
  <img src=images/create-product.png height=300/>
  <img src=images/my-orders.png height=300/>
  <img src=images/address.png height=300/>
</span>


## Planning and Testing

## Technologies Used

## Challenges Faced

## Key Learnings

Developing my first CRUD Spring Boot application has been a valuable learning experience. Through this project, I gained a deeper understanding of the Spring Boot framework, RESTful API development, CORS, and the fundamentals of database management. It allowed me to grasp the concepts of user authentication, JWT tokens, and integrating third-party APIs.

There were instances where the focus was on getting things working rather than finding the optimal solutions. As a result, the codebase may not be as clean and organised as i would have desired (especially in the frontend code).

This experience has underscored the significance of upholding clean and well-organised code.
The importance of prioritising writing clean, modular, and maintainable code. I understand that investing time and effort in creating an organised codebase pays off in the long run, benefiting not only myself but also for the entire development cycle/team.

Despite these challenges, the current project showcases the ability to deliver functional solutions within given constraints. It serves as a valuable learning experience and a foundation for future improvements, demonstrating the determination and adaptability required in software development.

## Continued development

Moving forward, the need to address weak input validation is apparent in order to enhance the overall reliability and security of the application. Validation will be a focal point, alongside other important aspects of development, to ensure a more robust and secure system.

In addition to strengthening validation logic, a focus on implementing continuous integration and continuous delivery (CI/CD) practices will be pursued. This approach will streamline testing and deployment processes, allowing for more efficient and reliable software development.

Optimising the user experience and design will also be a key priority. By refining the user interface (UI), improving navigation, and enhancing the overall user flow, the application will deliver an intuitive and visually appealing experience.

Security concerns will remain at the forefront, with the implementation of secure authentication and authorisation mechanisms, as well as measures to protect sensitive information and address potential vulnerabilities.

Considering the future direction of the application, exploring microservices architecture may be considered to leverage the benefits of scalability and maintainability.

Please note that this project utilises HTTP instead of HTTPS for simplicity in a local development environment.

The current implementation also stores sensitive configuration keys, such as API keys and database credentials, directly in the application's configuration files, such as application.yaml and application.properties. While this approach is suitable for local development and learning purposes, it is not recommended for production environments.

In a production environment, it is highly recommended to employ a configuration management system or a cloud-based service, such as AWS Secrets Manager or Azure Key Vault, to securely store and manage credentials. 
