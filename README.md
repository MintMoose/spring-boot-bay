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


## Continued development
