#This is a webApplication for handling flights information

It consists of two separate services, one that offers a front end user interface where the user can easily find and change flights data and one that provides API service endpoints
that can be used for retrieving and updating information about flights

This is a Maven and Spring Boot project and Spring Security has been applied.

To run the application simply clone the project, build it and run it.
It will start the application on localhost server with port 8080 as default

To see and update the flights information the user must be registered to the system.
This can only be done by an administrator that can add the user credentials to the system user database.

When user first goes to the main path  "localhost:8080/" is presented with a login form

After succesful login, the user can use the graphical user interface to search, update and delete flights.



#Usage of API

Only for administrator: Register new user with url  /api/register . Required parameters: username, password

Any registered user can do the following:

See all flights: /api/showAll

Update flight: /api/update  . Required parameter : id   Optional parameters: name,number,destination,departure,scheduledTime,arrivalTime,fare.

Delete flight: /api/delete . Required parameter :id

Add flight: /api/add   ..Required parameters :name,number,destination,departure,scheduledTime,arrivalTime,fare.
"The Id is given by the database"


##Caution!!
The dates parameters should be given in the format "yyyy-MM-dd'T'HH:mm"


###There are things that can be improved for certain but this is just the first prototype version of the application.

###Spring Security has been applied since it provides good encryption with BCryptPasswordEncoder and can easily configured for more user roles and authorities.







