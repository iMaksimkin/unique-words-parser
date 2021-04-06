#MySql database
FROM mysql:latest
LABEL maintainer="imaksimkin"
ENV MYSQL_ROOT_PASSWORD Password123
ENV MYSQL_DATABASE words_from_html
ENV MYSQL_USER  user123
ENV MYSQL_PASSWORD Password123

COPY config/my.cnf /etc/mysql/conf.d/my.cnf

EXPOSE 3306

#we can add volume and health check