# 🚀 GitHub Access Report Service

## 📌 Project Description

This project is a Spring Boot–based backend service that connects to the GitHub API and generates a clear, structured report showing **which users have access to which repositories within a given organization**.

In real-world organizations, especially large ones, it can become difficult to track who has access to what. This project aims to simplify that by aggregating repository-level data and presenting it in a clean, easy-to-understand JSON format.

Instead of manually checking each repository, this service automates the process and provides a single endpoint to fetch all access-related information.

---

## ⚙️ How to Run the Project

Running the project is straightforward. Follow these steps:

### 1. Clone the Repository

```bash
git clone https://github.com/Yashraj0103/github-access-report.git
cd github-access-report
```

---

### 2. Configure Environment Variable

Instead of storing sensitive credentials in code, the GitHub token is configured using an environment variable.

#### 👉 For PowerShell (Windows):

```bash
$env:GITHUB_TOKEN="your_github_token_here"
```

---

### 3. Run the Application

```bash
mvn spring-boot:run
```

If everything is set up correctly, you should see:

```text
Tomcat started on port 8080
```

---

### 4. Access the API

Open your browser or Postman and hit:

```bash
http://localhost:8080/api/report/{org}
```

Example:

```bash
http://localhost:8080/api/report/github
```

---

## 🔐 How Authentication is Configured

Authentication with GitHub is handled using a **Personal Access Token (PAT)**.

Instead of hardcoding the token inside the codebase (which is insecure), the application uses environment variables:

```properties
github.token=${GITHUB_TOKEN}
```

### Why this approach?

* Keeps sensitive data out of source code
* Prevents accidental exposure on GitHub
* Follows industry best practices

Whenever the application makes a request to GitHub, it automatically attaches this token in the request headers for authentication.

---

## 📡 How to Call the API Endpoint

The application exposes a single REST endpoint:

```bash
GET /api/report/{org}
```

### Example Request

```bash
http://localhost:8080/api/report/google
```

### What this does

* Fetches all repositories of the organization
* Retrieves users associated with each repository
* Aggregates the data into a user → repository mapping

---

### Sample Response

```json
{
  "user1": ["repo1", "repo2"],
  "user2": ["repo3"]
}
```

This means:

* `user1` has access to `repo1` and `repo2`
* `user2` has access to `repo3`

---

## 🧠 Assumptions & Design Decisions

### 🔹 1. Using Contributors Instead of Collaborators

GitHub restricts access to the collaborators API for many repositories unless you have explicit permissions.

Because of this, the project uses the **contributors API** as a practical approximation of access:

```bash
GET /repos/{org}/{repo}/contributors
```

👉 Assumption:

> Contributors are considered as users who have interacted with or have some level of access to the repository.

---

### 🔹 2. Parallel Processing for Scalability

To handle large organizations (100+ repositories, 1000+ users), the application uses:

* `parallelStream()` for concurrent processing

👉 This reduces execution time significantly compared to sequential calls.

---

### 🔹 3. Avoiding Duplicate Data

Instead of using a List, a Set is used internally:

```java
Map<String, Set<String>>
```

👉 This ensures:

* No duplicate repository entries
* Cleaner output

---

### 🔹 4. Layered Architecture

The project follows a clean structure:

```
Controller → Service → Client → GitHub API
```

* **Controller** → Handles API requests
* **Service** → Business logic and aggregation
* **Client** → External API calls
* **DTOs** → Data representation

👉 This makes the code easy to understand, maintain, and extend.

---

### 🔹 5. Error Handling Strategy

The application is designed to be resilient:

* Handles null or empty API responses
* Uses safe checks to prevent crashes
* Logs issues for debugging

---

## 🚀 Final Thoughts

This project demonstrates how to build a real-world backend service that:

* Integrates with an external API (GitHub)
* Handles scalability concerns
* Maintains clean architecture
* Follows security best practices

It also reflects practical problem-solving, especially in handling API limitations and designing around them.

---

## 💡 Future Improvements

If extended further, the following features can be added:

* Pagination support for large datasets
* Access roles (admin, write, read)
* Caching (e.g., Redis) to reduce API calls
* Swagger UI for API documentation
* Async calls using WebClient

---

✨ Overall, this project is a strong representation of backend development skills with a focus on real-world applicability, performance, and clean design.
