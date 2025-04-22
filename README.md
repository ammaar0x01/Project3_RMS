## Program: 	RMS, Restaurant-management System 
- Started	18.04.25
- Updated: 	22.04.25	
- Status: 	work-in-progress
- branch: 	MASTER	
```
Devs
----
- ammaar0x01
```
---

### Pre-requisites 
- Knowledge of the IntelliJ IDE
- Knowledge of Java-code
- Basic knowledge of Spring-boot code
- Have Java installed as an environment variable (this is used when running a JAR file)
---


### Download the app
```
git clone --branch [branch name] [git https URL] 
OR
Download the zipped folder
```

### Run the app
Using the IntelliJ IDE
1. Click the run button
2. Open the app in the browser; default ip address=localhost:8080 (http://localhost:8080)

JAR file
1. Open the command-line
2. Run `java -jar [JAR file]`
3. Open the app in the browser; default ip address=localhost:8080 (http://localhost:8080)

Batch file
- Run the batch script
---


### End-points/Routes
```
[
/, /{undefined-route}, 
/sign-up, /sign-in, 
/dashboard, /employees, /orders, /reservations, /profile,
/orders-only, /employees-list 
]
```
---


### Dependencies used (using the spring-initializer tool)
- Spring Web
- Thymeleaf
- Lombok
- Spring Dev tools

- spring-boot-starter-data-jpa
- spring-boot-starter-thymeleaf
- spring-boot-starter-validation
- spring-boot-starter-web
- spring-boot-devtools
- mysql-connector-j
- spring-boot-starter-test ?
---


### Screenshots 
![Screenshot 2025-04-22 065921.png](screenshots%2FScreenshot%202025-04-22%20065921.png)

![Screenshot 2025-04-22 065921.png](screenshots%2FScreenshot%202025-04-22%20065921.png)

![Screenshot 2025-04-22 065921.png](screenshots%2FScreenshot%202025-04-22%20065921.png)
---

### Video demo 
- sa 
---

### Convert to JAR file (in IntelliJ)
1. Navigate to the pom.xml file 
2. Click the 'M' (Maven) icon on the right side of the screen
3. Open the dropdown with the name of your java project 
4. Open the dropdown labeled 'Lifecycle'
5. Select the package option
6. If build is successful, the JAR file will be in the '.\target' directory 
---


### Deployment
- https://docs.spring.io/spring-boot/tutorial/first-application/index.html
- https://aditya-sunjava.medium.com/recommended-deployment-method-for-spring-boot-applications-in-a-production-environment-6fdd70dfc844
- (convert to JAR file) https://www.youtube.com/watch?v=L2y2XXI_d2w
---


### More help
- https://www.geeksforgeeks.org/returning-an-html-page-from-a-restful-controller-in-spring-boot/
- https://start.spring.io/
- https://www.youtube.com/watch?v=YDRNMAJo0MA
---
