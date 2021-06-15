# Drink tracker

### Important

Change database settings in application.properties to fit for your database.

API documentation is available at `/api/public/docs` (json or add .yaml 
for yaml) and documentation with ui at `/api/public/swagger`.

### Authentication System

The application uses simple spring security authentication and 
**Basic Auth** for API requests. Accessing pages requires you to be 
signed up and logged in (or include **Basic Auth** header).

Except these pages:

- `/api/public/docs`
- `/api/public/swagger`
- `/api/login`
- `/api/public/sign`
- `/login`
- `/public/sign`

You can log in via API POST request to  `/api/login` with 
username & password as parameters if you do not want to use headers
to authenticate.

### Charts Creation Page

The application uses **Google Charts** to generate charts. 
By default, charts generate from `/records` endpoint. You can
change the endpoint by filling it into the text input and 
submitting with a button.

**Use only endpoints. Do not prepend `/api`.**

Examples:

- `/users/Adam/records` Displays all records of Adam
- `/records/search/findByDrinkType?type=WATER` Displays all records, where the type is WATER 

### Example DDL

Don't forget to change the owner to the user, that spring uses to connect to the database.

```SQL
CREATE TABLE users (
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    PRIMARY KEY (username)
);

alter table users
    owner to spring;

CREATE TABLE drinks (
    id BIGSERIAL NOT NULL ,
    created timestamp,
    drink_type int4,
    volume int4 NOT NULL ,
    username VARCHAR(255) NOT NULL ,
    PRIMARY KEY (id),
    FOREIGN KEY (username)
        REFERENCES users
);

alter table drinks
    owner to spring;
```

### Example Insert

This creates 4 users with respective names `Adam`, `Bedrich`,
 `Cecilka` and `Dan`. Every user has password 
`heslo`.

```SQL
INSERT INTO users (username, password)
VALUES ('Adam','$2a$10$EsQAG8L2hJeEigCzOOUWL.fzlQ5oQGi39/Q4Y1fuD4.SBL7kHq0R2'),
('Bedrich','$2a$10$55WKC92Rk2TFDfqgRCZ6juj3fapGq7d7XpGKxpIQ1R30Th0PvYthe'),
('Cecilka','$2a$10$NNusWMSV8G8QBW3I88S5duzlPj4qTgG6V.IHVu3Jim5YSCoG9sgs6'),
('Dan','$2a$10$zRHiYQTrIeMQdkjEzMjVDuh1/ZBm4PmDJTozIsl.c/Q2BcZuy/.Ui');

INSERT INTO drinks (created, drink_type, volume, username)
VALUES
 ('2021-06-09 14:08:42.304684', 0, 250, 'Adam'),
 ('2021-06-05 16:27:32.891220', 0, 300, 'Adam'),
 ('2021-06-05 16:28:32.891220', 0, 300, 'Cecilka'),
 ('2021-06-05 16:28:34.891220', 0, 300, 'Dan'),
 ('2021-06-05 16:29:42.614229', 0, 300, 'Adam'),
 ('2021-06-05 19:09:05.038541', 1, 300, 'Adam'),
 ('2021-06-05 21:09:05.038541', 1, 300, 'Bedrich'),
 ('2021-06-06 22:30:06.059065', 0, 300, 'Adam'),
 ('2021-06-06 22:30:11.092051', 2, 300, 'Adam'),
 ('2021-06-06 22:30:11.192051', 2, 300, 'Dan'),
 ('2021-06-06 22:30:12.092051', 2, 300, 'Cecilka'),
 ('2021-06-06 22:30:14.811243', 1, 300, 'Adam'),
 ('2021-06-07 00:04:15.469994', 0, 350, 'Adam'),
 ('2021-06-08 00:04:24.759972', 0, 1000, 'Adam'),
 ('2021-06-08 00:04:30.759972', 0, 1000, 'Cecilka'),
 ('2021-06-09 00:04:33.424452', 0, 200, 'Adam'),
 ('2021-06-09 00:10:33.424452', 0, 200, 'Bedrich'),
 ('2021-06-09 14:08:42.304684', 3, 300, 'Adam'),
 ('2021-06-09 16:27:32.891220', 3, 300, 'Adam'),
 ('2021-06-09 16:28:32.891220', 3, 300, 'Dan'),
 ('2021-06-09 16:29:42.614229', 3, 250, 'Adam'),
 ('2021-06-09 18:20:42.614229', 3, 250, 'Cecilka'),
 ('2021-06-09 19:09:05.038541', 0, 100, 'Adam'),
 ('2021-06-09 22:30:06.059065', 1, 100, 'Adam'),
 ('2021-06-09 22:30:07.059065', 1, 100, 'Bedrich'),
 ('2021-06-09 22:30:08.059065', 1, 100, 'Cecilka'),
 ('2021-06-09 22:30:11.092051', 0, 250, 'Adam'),
 ('2021-06-09 22:30:14.811243', 2, 250, 'Adam'),
 ('2021-06-10 00:04:15.469994', 1, 300, 'Adam'),
 ('2021-06-10 00:04:18.469994', 1, 300, 'Dan'),
 ('2021-06-10 00:04:20.469994', 1, 300, 'Bedrich'),
 ('2021-06-10 00:04:24.759972', 0, 300, 'Adam'),
 ('2021-06-10 00:04:33.424452', 0, 300, 'Adam'),
 ('2021-06-10 10:04:33.424452', 0, 300, 'Dan');
```

### What to Improve

- Using Thymeleaf fragmentation would be preferred over normal HTML templates.
- Not handling POST requests with MVC at all
- Implement @Validator
- Add test requests
