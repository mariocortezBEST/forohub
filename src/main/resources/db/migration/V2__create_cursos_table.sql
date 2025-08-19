CREATE TABLE cursos (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        nombre VARCHAR(255) NOT NULL,
                        descripcion TEXT,
                        categoria VARCHAR(100) NOT NULL,
                        PRIMARY KEY (id)
);