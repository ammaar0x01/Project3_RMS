### Program: 
**RMS (Restaurant-management System)**
- Description: A web-app for managing a restaurant
- Tech-stack
    + Java 21 (Spring Boot)
    + SQL (MySQL work bench)
    + HTML
    + CSS
    + JS
--- 

- Started	18.04.25
- Updated: 	16.06.25
- Status: 	work-in-progress
- Branch: 	230159478
- Developer: 	ammaar0x01
---

### Pre-requisites 
```
- Should have Java installed (Version 21 used for this project)
- Should have IntelliJ installed 
- Should have MysQL Workbench installed 

- Knowledge of Java-code
- Knowledge of the IntelliJ IDE
- Basic knowledge of Spring-boot code
- Have Java installed as an environment variable (this is used when running a JAR file)
```
---


### Download the program
```
git clone --branch [branch name] [git https URL] 
OR
Download the zipped folder
```

### Run the program
Using the IntelliJ IDE
1. Click the run button
2. Open the app in the browser; default ip address=localhost:8080 (http://localhost:8080)

JAR file
1. Open the command-line
2. Run `java -jar [JAR file]`
3. Open the app in the browser; default ip address=localhost:8080 (http://localhost:8080)

OR 
<br >
Run the batch script to run the JAR file
---

### ERD 
![ERD.jpg](ERD.jpg)
---

### Dependencies used (using the spring-initializer tool)
```
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
``` 
---


### Screenshots

![Screenshot 2025-04-22 065935.png](screenshots%2FScreenshot%202025-04-22%20065935.png)

![Screenshot 2025-04-22 065941.png](screenshots%2FScreenshot%202025-04-22%20065941.png)
---


### Convert to JAR file (in IntelliJ)
1. Navigate to the pom.xml file 
2. Click the 'M' (Maven) icon on the right side of the screen
3. Open the dropdown with the name of your java project 
4. Open the dropdown labeled 'Lifecycle'
5. Select the 'package' option
6. If build is successful, the JAR file will be in the '.\target' directory 
---


### Deployment
- https://docs.spring.io/spring-boot/tutorial/first-application/index.html
- https://aditya-sunjava.medium.com/recommended-deployment-method-for-spring-boot-applications-in-a-production-environment-6fdd70dfc844
- (convert to JAR file) https://www.youtube.com/watch?v=L2y2XXI_d2w
---


### More help (references)
- https://www.geeksforgeeks.org/returning-an-html-page-from-a-restful-controller-in-spring-boot/
- https://start.spring.io/
- https://www.youtube.com/watch?v=YDRNMAJo0MA
---
