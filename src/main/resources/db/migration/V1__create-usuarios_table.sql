CREATE TABLE usuarios (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          nombre VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          rol VARCHAR(50) NOT NULL,
                          fecha_creacion DATETIME NOT NULL,
                          PRIMARY KEY (id)
);