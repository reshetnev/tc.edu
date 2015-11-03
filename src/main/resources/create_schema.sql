CREATE TABLE IF NOT EXISTS States (stateId INT IDENTITY PRIMARY KEY,
                                   badge VARCHAR(255) NOT NULL);
CREATE TABLE IF NOT EXISTS Categories (categoryId INT IDENTITY PRIMARY KEY,
                                       category VARCHAR(255) NOT NULL);
CREATE TABLE IF NOT EXISTS Courses (id INT IDENTITY PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL,
                                    stateId INT NOT NULL,
                                    FOREIGN KEY (stateId) REFERENCES States(stateId),
                                    categoryId INT NOT NULL,
                                    FOREIGN KEY (categoryId) REFERENCES Categories(categoryId),
                                    voteKM INT NOT NULL,
                                    voteDM INT NOT NULL,
                                    reasonKM VARCHAR(255) NOT NULL,
                                    reasonDM VARCHAR(255) NOT NULL);
CREATE TABLE IF NOT EXISTS Details (
                                    id INT IDENTITY PRIMARY KEY, FOREIGN KEY (id) REFERENCES Courses(id),
                                    lecturer VARCHAR(255) NOT NULL,
                                    description VARCHAR(255) NOT NULL,
                                    links VARCHAR(255) NOT NULL,
                                    qSubs INT NOT NULL,
                                    qAtts INT NOT NULL);                         
CREATE TABLE IF NOT EXISTS Roles (roleId INT IDENTITY PRIMARY KEY,
                                  role VARCHAR(255) NOT NULL);
CREATE TABLE IF NOT EXISTS Users (userId INT IDENTITY PRIMARY KEY,
                                  login VARCHAR(255) NOT NULL,
                                  email VARCHAR(255) NOT NULL,
                                  password VARCHAR(255) NOT NULL,
                                  roleId INT NOT NULL,
                                  FOREIGN KEY (roleId) REFERENCES Roles(roleId));
CREATE TABLE IF NOT EXISTS Subscribes (
                                       id INT NOT NULL,
                                       FOREIGN KEY (id) REFERENCES Courses(id),
                                       userId INT NOT NULL,
                                       FOREIGN KEY (userId) REFERENCES Users(userId),
                                       PRIMARY KEY (id, userId)
                                       );
CREATE TABLE IF NOT EXISTS Attends (
                                    id INT NOT NULL,
                                    FOREIGN KEY (id) REFERENCES Courses(id),
                                    userId INT NOT NULL,
                                    FOREIGN KEY (userId) REFERENCES Users(userId),
                                    grade INT NOT NULL,
                                    PRIMARY KEY (id, userId)
                                    );