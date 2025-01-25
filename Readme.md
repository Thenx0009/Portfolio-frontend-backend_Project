# Portfolio Tracker Application

A feature-rich web application designed to help users manage their stock portfolios, track real-time stock prices, and visualize performance metrics. Built with modern web technologies and deployed using cloud-based services, the application offers seamless and dynamic portfolio management.

---

## Features

### Frontend
- **Dashboard**:
  - Displays:
    - Total portfolio value updated in real-time.
    - Top-performing stock by percentage gain.
    - Portfolio distribution visualization with a responsive pie chart.
- **Stock Management**:
  - Add, edit, and delete stocks with an intuitive form-based interface.
  - View and interact with a detailed table displaying stock details, including:
    - Stock name, ticker, quantity, buy price, current price, and P&L (Profit/Loss).
- **Wishlist**:
  - Save stocks for later tracking with local persistence using `localStorage`.
  - Manage wishlist visibility and actions like removing stocks or buying from the wishlist.
- **Real-Time Price Checker**:
  - Fetch real-time stock prices using a ticker symbol with API integration.
- **Responsive Design**:
  - Built with React and TailwindCSS to provide a smooth experience across devices.

### Backend
- **RESTful API**:
  - Endpoints for CRUD operations on stocks and portfolio entries.
  - Real-time stock price fetching using the **Finnhub API**.
  - Dashboard API for portfolio insights, including:
    - Total portfolio value.
    - Top-performing stock.
    - Portfolio distribution.
- **Dynamic Calculations**:
  - Portfolio value and metrics updated dynamically using real-time stock prices.
- **Security**:
  - CORS configured to allow secure interaction with the frontend.
- **Database**:
  - PostgreSQL with JPA and Hibernate for efficient and scalable data management.

### Deployment
- **Frontend**: Hosted on **Vercel**.
- **Backend**: Deployed on **Render** with a Dockerized image.
- **Database**: Hosted on a PostgreSQL instance locally or via a cloud provider.
- **Docker**: Backend containerized for portability and ease of deployment.

---

## Tech Stack

### Frontend
- React
- React Router
- TailwindCSS
- Framer Motion (for animations)
- Chart.js (for portfolio distribution pie chart)

### Backend
- Java with Spring Boot
- PostgreSQL
- JPA & Hibernate
- RestTemplate (for Finnhub API integration)

### APIs
- Finnhub API for fetching real-time stock prices.

### Deployment
- **Frontend**: Vercel
- **Backend**: Render
- **Containerization**: Docker

---

## Project Structure


