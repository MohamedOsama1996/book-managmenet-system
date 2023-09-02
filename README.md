#Online Book Management System 

#How to run the project 

1- open the terminal
2-write the following command in the cmd : docker pull mcr.microsoft.com/mssql/server
3- After pulling the docker image , run the following command to run the image :
docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Banquemisr123" -p 1433:1433 --name sql_server_container -d mcr.microsoft.com/mssql/server to run
the image in a container 
4- clone the project from github and run the application 
5- visit : localhost:8080/swagger-ui/index.html to see swagger documentation of the service


