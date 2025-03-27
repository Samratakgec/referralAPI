# Referral API - Spring Boot & MongoDB

This is a **Spring Boot**-based **Referral System** API that uses **MongoDB** as the database. It provides functionalities for user registration, referral tracking, and user profile management.

## Tech Stack
- **Backend:** Spring Boot, Java
- **Database:** MongoDB
- **Frameworks/Libraries:** Spring Data MongoDB, Spring Web
- **Tools:** Maven

---

## API Endpoints

### 1. Referral Controller (`/api/referral/`)
| HTTP Method | Endpoint       | Description |
|-------------|--------------|-------------|
| `GET`       | `/track`       | Fetch successful and pending users of a particular referral code of a particular user |
| `GET`       | `/download`    | Download all referrals as a CSV file |

---

### 2. User Controller (`/api/user/`)
| HTTP Method | Endpoint       | Description |
|-------------|--------------|-------------|
| `POST`      | `/sign-up`     | Create a new user with an optional referral code |
| `GET`       | `/login`       | Log in a user |
| `GET`       | `/logout`      | Log out the current user |

---

### 3. User Profile Controller (`/api/userProfile/`)
| HTTP Method | Endpoint       | Description |
|-------------|--------------|-------------|
| `POST`      | `/new`         | Create a user profile and update referral records |

---

## Key Components

### 1. Models
- **`User`**: Represents a user entity.
- **`Referral`**: Tracks referrals and their statuses.
- **`UserProfile`**: Stores user profile details.

### 2. Services
- **`UserInterface`**: Handles user-related operations.
- **`ReferralInterface`**: Manages referral logic.
- **`UserProfileInterface`**: Manages user profiles.

### 3. Utilities
- **`UserSession`**: Maintains user session data such as user's object id and have functionality to set and clear user's object id data from system.

---

## How to Run the Project

### 1. Prerequisites
Ensure you have the following installed:
- **Java 17+**
- **Maven**
- **MongoDB**

### 2. Steps to Run
1. **Clone the repository**
   ```bash
   git clone https://github.com/your-repo/referralAPI.git
   cd referralAPI
2. **Run Mongo db**
   Run following command in cmd (open it as administrator)
   ``` bash
   mongod --dbpath "C:\data\db"
3. **Run Server**
   Navigate to referralAPI\src\main\java\com\samrat\referralAPI and run ReferralApiApplication.java. file

# Referral API - Postman API Testing Guide

## API Endpoints & Request Bodies

This document provides example request bodies for testing the Referral API using **Postman**.

---

## **1. User Signup**
### **Endpoint:** `POST /api/user/sign-up`
### **Request Body (JSON)**
```json
{
    "email" : "abc@gmail.com",
    "password" : "sbc",
    "refGain" : "67e4f199bb0b6959a5b0f2e3"
}
```
## **2. User Login**
### **Endpoint:** `GET /api/user/login`
```json
{
    "email" : "abc@gmail.com",
    "password" : "sbc",
    "refGain" : "67e4f199bb0b6959a5b0f2e3"
}
```
## **3. User Logout**
### **Endpoint:** `GET /api/user/logout`

## **4. Create User Profile**
### **Endpoint:** `POST /api/userProfile/new`  
 
```json
{
    "name" : "Samrat" ,
    "phoneNumber" : "920887423",
    "bio" : "i am a Java backend developer"
}
```
## **5. Track successfull users and pending users of a referral code of a user**
### **Endpoint:** `GET http://localhost:8080/api/referral/track`  

## 6. Download All Referral Records in CSV Format  

### **Endpoint:**  
`GET http://localhost:8080/api/referral/download`  

### **Note:**  
Make sure to hit this endpoint in a **browser**, so the CSV file gets downloaded to your system.  

