CREATE TABLE "users" (
id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
username VARCHAR(50),
email VARCHAR(255),
password VARCHAR(255),
role VARCHAR(50),
deleted_at TIMESTAMP DEFAULT NULL,
CONSTRAINT username_unique UNIQUE (username)
);

CREATE TABLE "game" (
id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
start_time TIMESTAMP,
end_time TIMESTAMP,
winner_id INTEGER DEFAULT NULL,
CONSTRAINT fk_game_winner
    FOREIGN KEY(winner_id)
	REFERENCES users(id)
);

CREATE TABLE "row" (
id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
row_number INTEGER,
game_id INTEGER,
CONSTRAINT fk_row_game
    FOREIGN KEY(game_id)
    REFERENCES game(id)
);

CREATE TABLE "throw" (
id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
row_id INTEGER,
dart_number INTEGER,
score INTEGER,
thrower_id INTEGER,
CONSTRAINT fk_throw_row
    FOREIGN KEY(row_id)
    REFERENCES "row"(id),
CONSTRAINT fk_throw_thrower
    FOREIGN KEY(thrower_id)
    REFERENCES users(id)
);