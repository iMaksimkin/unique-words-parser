This is the project created by [imaksimkin](int-8@yandex.ru)

It gets the unique words from a website and put them into the database. 
Firstly you need to apply the following command to create the container with database:   **docker run  --name imaksimkin-mysql -p 3307:3306  imaksimkin/imaksimkin-mysql:latest**
If you have no installed docker please change the property file from the resource/properties/ folder with your connection configuration to MySQL/MariaDB database. 
Note: you should use the following command to create the DB  CREATE DATABASE IF NOT EXISTS words_from_html

Then run the main (ru/imaksimkin/runner/main.java) file in the current project and chose the value of the site you want to parse in the terminal.
The html page will be saved in the target/ folder.

For now, we do not support URLs with Internationalized Domain Names


####How to execute
- from terminal: docker run -d --name imaksimkin-mysql -p 3307:3306  imaksimkin/imaksimkin-mysql:latest
- from terminal: mvn clean package
- from terminal: java -jar target/IMaksimkinProject-0.1-SNAPSHOT-jar-with-dependencies.jar


