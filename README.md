This is the project created by [imaksimkin](int-8@yandex.ru)

It gets the unique words from a website and put them into database. 
Firstly you need to apply the following command to create the container with database:   **docker run  --name imaksimkin-mysql -p 3307:3306  imaksimkin/imaksimkin-mysql:latest**
or change the connection in properties to your local.

Then you should use the main (ru/imaksimkin/runner/main.java) file in the current project and chose the value of the site you want to parse in the terminal.

For now, we doesnt support URLs with Internationalized Domain Names