# Library Management System

This is a RESTful API for a Library Management System built using Spring Boot. The application manages books, students, and borrowing transactions, with comprehensive validation and error handling. The system uses MySQL as the database to store and manage data.



## Features
- Manage books, students, and borrowing records.
- Filter books by genre.
- Retrieve students who borrowed a specific book.
- Validate borrowing requests based on specific conditions:
  - Check if student and book IDs exist.
  - Ensure a student does not have more than two unreturned books.
  - Maintain at least one copy of a book available (not for lending).
  - Decrease available book copies upon borrowing.
- Return meaningful error messages for failed validations.
- Centralized exception handling for robust error management.

## Technologies Used
- **Spring Boot**: Framework for building the RESTful API.
- **Spring Data JPA**: For database operations and repository management.
- **MySQL**: Relational database for storing data.
- **Maven**: Dependency management and build tool.
- **Java 17**: Programming language.
- **Lombok**: To reduce boilerplate code.

## Setup Instructions
1. **Prerequisites**:
   - Java 17 or higher
   - MySQL 8.0 or higher
   - Maven
   - IDE (e.g., IntelliJ IDEA, Eclipse)
   - Postman or any API testing tool

2. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-repo/library-management-system.git
   cd library-management-system
   ```

3. **Configure MySQL**:
   - Create a MySQL database named `library_db`.
   - Update the `application.properties` file with your MySQL credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/library_db
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     ```

4. **Build and Run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   The application will start on `http://localhost:8080`.

## Database Schema
The database consists of three main entities: `Student`, `Book`, and `Borrow`.

### Student
| Column       | Type         | Description                     |
|--------------|--------------|---------------------------------|
| id           | VARCHAR(50)  | Primary key, unique student ID  |
| name         | VARCHAR(100) | Student's name                 |
| department   | VARCHAR(100) | Student's department           |
| year         | INTEGER      | Year of study                  |

### Book
| Column           | Type         | Description                     |
|------------------|--------------|---------------------------------|
| id               | VARCHAR(50)  | Primary key, unique book ID    |
| title            | VARCHAR(100) | Book title                     |
| author           | VARCHAR(100) | Book author                    |
| isbn             | VARCHAR(13)  | ISBN number                    |
| copiesAvailable  | INTEGER      | Number of available copies     |
| genre            | VARCHAR(50)  | Book genre                     |
| publishedDate    | DATETIME     | Publication date               |

### Borrow
| Column       | Type         | Description                     |
|--------------|--------------|---------------------------------|
| id           | BIGINT       | Primary key, auto-increment     |
| student_id   | VARCHAR(50)  | Foreign key to Student         |
| book_id      | VARCHAR(50)  | Foreign key to Book            |
| borrowDate   | DATETIME     | Date the book was borrowed     |
| returnDate   | DATETIME     | Date the book was returned     |
| returned     | VARCHAR(10)  | Status (YES/NO)                |

### Insert Sample Data
Run the following SQL commands in MySQL to insert the sample data:

```sql
-- Students
INSERT INTO student (id, name, department, year) VALUES
('stu1', 'Alice Smith', 'Computer Science', 2),
('stu4', 'David Kim', 'Computer Science', 4);

-- Books
INSERT INTO book (id, title, author, isbn, copies_available, genre, published_date) VALUES
('book1', 'Clean Code', 'Robert C. Martin', '9780132350884', 2, 'Programming', '2008-08-10 18:30:00'),
('book3', 'You Don\'t Know JS', 'Kyle Simpson', '9781491904244', 1, 'JavaScript', '2015-12-26 18:30:00'),
('book4', 'Eloquent JavaScript', 'Marijn Haverbeke', '9781593279509', 4, 'JavaScript', '2018-12-03 18:30:00');

-- Borrows
INSERT INTO borrow (id, student_id, book_id, borrow_date, return_date, returned) VALUES
(1, 'stu1', 'book1', '2025-04-30 18:30:00', NULL, 'NO'),
(6, 'stu4', 'book1', '2025-07-01 22:24:18', NULL, 'NO');
```

### Display Data in MySQL
To view the data, run:
```sql
SELECT * FROM student;
SELECT * FROM book;
SELECT * FROM borrow;
```

## API Endpoints
| Method | Endpoint                              | Description                              |
|--------|---------------------------------------|------------------------------------------|
| GET    | `/book/genre/{genre}`                | Filter books by genre                    |
| GET    | `/bor/stu/{bookId}`                  | Get students who borrowed a specific book |
| POST   | `/bor`                               | Borrow a book (with validations)         |

### Sample Requests
1. **Filter Books by Genre**:
   ```
   GET http://localhost:8080/book/genre/JavaScript
   ```
   Response:
   ```json
   [
       {
           "id": "book3",
           "title": "You Don't Know JS",
           "author": "Kyle Simpson",
           "isbn": "9781491904244",
           "copiesAvailable": 1,
           "genre": "JavaScript",
           "publishedDate": "2015-12-26T18:30:00.000+00:00"
       },
       {
           "id": "book4",
           "title": "Eloquent JavaScript",
           "author": "Marijn Haverbeke",
           "isbn": "9781593279509",
           "copiesAvailable": 4,
           "genre": "JavaScript",
           "publishedDate": "2018-12-03T18:30:00.000+00:00"
       }
   ]
   ```

2. **Get Students Who Borrowed a Book**:
   ```
   GET http://localhost:8080/bor/stu/book1
   ```
   Response:
   ```json
   [
       {
           "id": 1,
           "student": {
               "id": "stu1",
               "name": "Alice Smith",
               "department": "Computer Science",
               "year": 2
           },
           "book": {
               "id": "book1",
               "title": "Clean Code",
               "author": "Robert C. Martin",
               "isbn": "9780132350884",
               "copiesAvailable": 2,
               "genre": "Programming",
               "publishedDate": "2008-08-10T18:30:00.000+00:00"
           },
           "borrowDate": "2025-04-30T18:30:00.000+00:00",
           "returnDate": null,
           "returned": "NO"
       },
       {
           "id": 6,
           "student": {
               "id": "stu4",
               "name": "David Kim",
               "department": "Computer Science",
               "year": 4
           },
           "book": {
               "id": "book1",
               "title": "Clean Code",
               "author": "Robert C. Martin",
               "isbn": "9780132350884",
               "copiesAvailable": 2,
               "genre": "Programming",
               "publishedDate": "2008-08-10T18:30:00.000+00:00"
           },
           "borrowDate": "2025-07-01T22:24:18.000+00:00",
           "returnDate": null,
           "returned": "NO"
       }
   ]
   ```

3. **Borrow a Book**:
   ```
   POST http://localhost:8080/bor
   Content-Type: application/json
   {
       "bookId": "book1",
       "studentId": "stu4"
   }
   ```
   Response (Success):
   ```json
   {
       "message": "Book borrowed successfully"
   }
   ```

## Validation Rules
For borrowing a book (`POST /bor`):
1. **Student and Book Existence**: Both `studentId` and `bookId` must exist in the database.
2. **Unreturned Books Limit**: A student cannot have more than two unreturned books.
3. **Minimum Copies**: The book must have at least two copies available to allow borrowing (one copy must remain in the library).
4. **Update Copies**: Decrease the `copiesAvailable` by one upon successful borrowing.

### Error Responses
- **Student/Book Not Found**:
  ```json
  {
      "status": 404,
      "message": "Student with ID stuX not found"
  }
  ```
  or
  ```json
  {
      "status": 404,
      "message": "Book with ID bookX not found"
  }
  ```
- **Too Many Unreturned Books**:
  ```json
  {
      "status": 400,
      "message": "Student has already borrowed the maximum number of books (2)"
  }
  ```
- **Insufficient Copies**:
  ```json
  {
      "status": 400,
      "message": "Not enough copies available for borrowing"
  }
  ```

## Exception Handling
The application uses a centralized exception handler (`GenericExceptionHandler`) to manage errors:
- **EntityNotFoundException**: Handles missing student or book IDs (HTTP 404).
- **DuplicateKeyException**: Handles duplicate entries (HTTP 400).
- **General Exceptions**: Catches unexpected errors (HTTP 500).

### Exception Handler Code
```java
package com.example.demo.exceptionhandler;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.model.ErrorResponce;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GenericExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponce> handleEntityNotFound(EntityNotFoundException exception) {
        ErrorResponce errorResponse = new ErrorResponce(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponce> handleDuplicateKey(DuplicateKeyException exception) {
        ErrorResponce errorResponse = new ErrorResponce(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponce> handleCommonExceptions(Exception exception) {
        ErrorResponce errorResponse = new ErrorResponce(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

## Running the Application
1. Ensure MySQL is running and the database is set up.
2. Run the application using:
   ```bash
   mvn spring-boot:run
   ```
3. The API will be available at `http://localhost:8080`.

## Testing the API
Use Postman or cURL to test the endpoints. Sample cURL commands:

1. **Filter Books by Genre**:
   ```bash
   curl http://localhost:8080/book/genre/JavaScript
   ```

2. **Get Students Who Borrowed a Book**:
   ```bash
   curl http://localhost:8080/bor/stu/book1
   ```

3. **Borrow a Book**:
   ```bash
   curl -X POST http://localhost:8080/bor -H "Content-Type: application/json" -d '{"bookId":"book1","studentId":"stu4"}'
   ```
