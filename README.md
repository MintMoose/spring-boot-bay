# Java Spring Boot Ecommerce React Website

## Summary
This project is a full-stack application built with Spring Boot and React. It features a secure user authentication system implemented with Spring Security and JWT (JSON Web Tokens). User information, addresses, and orders are stored in a PostgreSQL database using docker.

The frontend, developed with React, provides a user-friendly interface for browsing and purchasing products. It leverages the Stripe API to handle secure payment transactions. The backend, powered by Spring Boot, integrates with the Stripe API and manages the creation of checkout sessions.


## Table of contents

  - [Screenshot](#screenshots)
  - [Technologies Used](#technologies-used)
  - [Planning and Testing](#planning-and-testing)
  - [Challenges Faced](#challenges-faced)
  - [Key Learnings](#key-learnings)
  - [Continued development](#continued-development)

## Screenshots
<span>
  <img src=images/homepage-loggedIn.png height=400/>
  <img src=images/product-page-mobile.jpg height=400/>
    <img src=images/CheckoutPageAll.jpg height=300/>
  <img src=images/my-products.jpg height=300/>
<img src=images/productPageBoth.jpg height=330/>
  <img src=images/create-product.png height=330/>
  <img src=images/products-list.jpg height=300/>
  
  <img src=images/my-orders.png height=300/>
  <img src=images/address.png height=300/>
</span>


## Technologies Used

- Frontend:
  - React
  - HTML
  - CSS
  - JavaScript

- Backend:
  - Spring Boot
  - Spring Security
  - JWT (JSON Web Tokens)
  - PostgreSQL (as the database)

- API Integration:
  - Stripe API (for secure payment transactions)

- Testing:
  - JUnit
  - Mockito

- Deployment and Environment:
  - Docker (for containerization)

- Version Control:
  - Git
  - GitHub 

- Development Tools:
  - IDE (IntelliJ, Visual Studio Code)
  - Package Managers (npm, Maven)
 
## Planning and Testing
<span>
    <img src=images/spring-event-combined.png height=300/>
<img src=images/erd_psql_db.png height=330/>
  <img src=images/test-coverage.png height=300/>
  <img src=images/tests-ran.png height=300/>
</span>

## Challenges Faced

### Implementing Spring Security

Implementing Spring Security to secure the application posed a significant challenge during the development process. While Spring Security provides powerful features for authentication and authorisation, its configuration and customisation options can be overwhelming, especially for developers new to the framework. Ensuring the correct integration of Spring Security with the application's existing components and user authentication system required careful planning and testing.

The complexity of defining access rules, roles, and permissions, as well as handling user sessions and logout functionality, demanded a deep understanding of Spring Security's mechanisms. Additionally, customising error handling and login processes to provide a seamless user experience while maintaining security added to the complexity.

### Security Vulnerabilities

Addressing security vulnerabilities was a top priority throughout the development of the application. Identifying potential security risks, such as SQL injection, Cross-Site Scripting (XSS), and Cross-Site Request Forgery (CSRF), was an ongoing challenge. While Spring Security helps protect against common threats, it was essential to implement proper input validation, parameterised queries, and data sanitization to prevent security breaches.

Furthermore, staying updated on the latest security best practices and patches for third-party dependencies was crucial to maintain a secure codebase. Regular security audits and vulnerability assessments were performed to identify and mitigate any potential weaknesses in the application's defenses.

### Cross-Origin Resource Sharing (CORS)

Handling Cross-Origin Resource Sharing (CORS) was a significant obstacle, especially when developing the frontend and backend as separate components. Ensuring that the frontend could securely communicate with the backend while preventing unauthorised access demanded meticulous configuration and testing.

Configuring CORS policies correctly required understanding the security implications of allowing or restricting cross-origin requests. Striking the right balance between providing access to legitimate cross-origin requests and blocking potential security threats was a delicate process.

## Key Learnings

Developing my first CRUD Spring Boot application has been a valuable learning experience. Through this project, I gained a deeper understanding of the Spring Boot framework, RESTful API development, CORS, and the fundamentals of database management. It allowed me to grasp the concepts of user authentication, JWT tokens, and integrating third-party APIs.

There were instances where the focus was on getting things working rather than finding the optimal solutions. As a result, the codebase may not be as clean and organised as i would have desired (especially in the frontend code).

This experience has underscored the significance of upholding clean and well-organised code.
The importance of prioritising writing clean, modular, and maintainable code. I understand that investing time and effort in creating an organised codebase pays off in the long run, benefiting not only myself but also for the entire development cycle/team.

Despite these challenges, the current project showcases the ability to deliver functional solutions within given constraints. It serves as a valuable learning experience and a foundation for future improvements, demonstrating the determination and adaptability required in software development.

## Continued development

Moving forward, to further enhance the application's reliability and security, additional integration tests will be implemented to thoroughly validate the interactions between different components, including those with authentication and authorisation features. These tests will play a crucial role in ensuring the application's stability and responsiveness, allowing for a comprehensive assessment of its functionality under real-world scenarios. By addressing weak input validation, expanding the test suite, and following industry best practices, the project aims to deliver a robust, user-friendly, and secure ecommerce platform for its users.

In addition to strengthening validation logic, a focus on implementing continuous integration and continuous delivery (CI/CD) practices will be pursued. This approach will streamline testing and deployment processes, allowing for more efficient and reliable software development.

Optimising the user experience and design will also be a key priority. By refining the user interface (UI), improving navigation, and enhancing the overall user flow, the application will deliver an intuitive and visually appealing experience.

Security concerns will remain at the forefront, with the implementation of secure authentication and authorisation mechanisms, as well as measures to protect sensitive information and address potential vulnerabilities.

Considering the future direction of the application, exploring microservices architecture may be considered to leverage the benefits of scalability and maintainability.

Please note that this project utilises HTTP instead of HTTPS for simplicity in a local development environment.

The current implementation also stores sensitive configuration keys, such as API keys and database credentials, directly in the application's configuration files, such as application.yaml and application.properties. While this approach is suitable for local development and learning purposes, it is not recommended for production environments.

In a production environment, it is highly recommended to employ a configuration management system or a cloud-based service, such as AWS Secrets Manager or Azure Key Vault, to securely store and manage credentials. 
