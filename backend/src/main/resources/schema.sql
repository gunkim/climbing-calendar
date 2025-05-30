CREATE TABLE users
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    email         VARCHAR(255) NOT NULL UNIQUE,
    name          VARCHAR(100) NOT NULL,
    profile_image VARCHAR(500),
    deleted_at    TIMESTAMP DEFAULT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE climbing_gym
(
    id                   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name                 VARCHAR(255) NOT NULL,
    address              VARCHAR(500) NOT NULL,
    latitude             DOUBLE       NOT NULL,
    longitude            DOUBLE       NOT NULL,
    is_parking_available BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at           TIMESTAMP        DEFAULT NULL,
    created_at           TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE level
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    climbing_gym_id BIGINT      NOT NULL,
    color           VARCHAR(50) NOT NULL,
    start_grade     INT         NOT NULL,
    end_grade       INT         NOT NULL,
    deleted_at      TIMESTAMP DEFAULT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (climbing_gym_id) REFERENCES climbing_gym (id)
);

CREATE TABLE schedule
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id         BIGINT       NOT NULL,
    climbing_gym_id BIGINT       NOT NULL,
    title           VARCHAR(255) NOT NULL,
    memo            TEXT,
    schedule_date   TIMESTAMP    NOT NULL,
    deleted_at      TIMESTAMP DEFAULT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (climbing_gym_id) REFERENCES climbing_gym (id)
);

CREATE TABLE clear
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT NOT NULL,
    level_id    BIGINT NOT NULL,
    count       INT    NOT NULL,
    deleted_at  TIMESTAMP DEFAULT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (schedule_id) REFERENCES schedule (id),
    FOREIGN KEY (level_id) REFERENCES level (id)
);

