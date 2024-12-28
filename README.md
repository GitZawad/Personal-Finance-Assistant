# Income and Expenditure Management System

## **Overview**
The Income and Expenditure Management System is a Java-based desktop application designed to help users manage their financial transactions efficiently. The system supports two types of roles: **Administrator** and **User**. Administrators can manage users and transaction categories, while users can record, view, and analyze their income and expenditure details.

## **Features**
### **Administrator Role**
1. **User Management**:
   - Add new users (username must be unique).
   - Delete existing users.
   - View personal information of all users.

2. **Income Category Management**:
   - Create, retrieve, update, and delete income categories (e.g., Salary, Part-time Income, Investment Income).

3. **Expenditure Category Management**:
   - Create, retrieve, update, and delete expenditure categories (e.g., Dining, Travel, Education, Healthcare).

4. **Exception Handling**:
   - Handles errors such as invalid input or duplicate usernames.

### **User Role**
1. **Transaction Management**:
   - Add income records (category, date, amount).
   - Add expenditure records (category, date, amount).

2. **Financial Analysis**:
   - Calculate total income, total expenditure, and balance for a specific day, month, or year.
   - View transaction history sorted by amount (descending) and category (alphabetically for ties).

3. **Data Export**:
   - Export and save all income and expenditure records to a file.

4. **Exception Handling**:
   - Handles invalid numeric inputs and incorrect login credentials.

## **Technologies Used**
- **Programming Language**: Java
- **Database**: MySQL
- **Database Connectivity**: JDBC
- **IDE**: IntelliJ IDEA
- **Version Control**: Git and GitHub

## **Setup Instructions**
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/income-expenditure-management.git
   ```
2. **Configure Database**:
   - Install MySQL and create a database named `finance_db`.
   - Execute the SQL script `database.sql` (included in the repository) to set up tables.

3. **Update Database Credentials**:
   - Open the `DatabaseConnection.java` file.
   - Update the `DB_URL`, `USERNAME`, and `PASSWORD` constants to match your MySQL configuration.

4. **Run the Application**:
   - Compile and run the `Main.java` file in your IDE or terminal.

## **Database Structure**
- **Tables**:
  - `users`: Stores user login credentials and roles.
  - `income_categories`: Stores income category details.
  - `expenditure_categories`: Stores expenditure category details.
  - `transactions`: Stores user income and expenditure records.

## **Future Enhancements**
- Integrate a REST API for web-based access.
- Add data visualization for financial insights.
- Support multi-language user interfaces.
- Implement password encryption for enhanced security.

## **Screenshots**
Add screenshots of the application interface here (e.g., login page, admin panel, user dashboard).

## **Contributing**
Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add feature-name"
   ```
4. Push to the branch:
   ```bash
   git push origin feature-name
   ```
5. Create a Pull Request.

## **License**
This project is licensed under the MIT License. See the `LICENSE` file for details.

## **Contact**
For questions or suggestions, feel free to reach out:
- **Email**: your-email@example.com
- **GitHub**: [your-username](https://github.com/your-username)

---

Replace placeholders like `your-username` and `your-email@example.com` with your actual GitHub username and email. This README will make your project look polished and professional!

