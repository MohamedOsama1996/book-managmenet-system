Online Book Management System
How to Run the Project
Open the terminal.

Pull the SQL Server Docker image by running the following command:

docker pull mcr.microsoft.com/mssql/server

After pulling the Docker image, run the following command to create and run a Docker container:

docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Banquemisr123" -p 1433:1433 --name sql_server_container -d mcr.microsoft.com/mssql/server
This command creates a Docker container for SQL Server with the specified environment variables, password, and port mapping.

Clone the project from GitHub to your local machine.

Run the application on your local machine.

Visit localhost:8080/swagger-ui/index.html in your web browser to access the Swagger documentation for the service.

