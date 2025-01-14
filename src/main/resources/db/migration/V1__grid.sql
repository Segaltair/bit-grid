CREATE TABLE grid (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    cell_size INT NOT NULL CHECK (cell_size BETWEEN 0 AND 100000)
);

CREATE TABLE grid_column (
    id BIGSERIAL PRIMARY KEY,
    number INT NOT NULL CHECK (number > 0),
    grid_id BIGINT NOT NULL REFERENCES grid,
    UNIQUE (grid_id, number)
);

CREATE TABLE grid_row (
    id BIGSERIAL PRIMARY KEY,
    number INT NOT NULL CHECK (number > 0),
    grid_id BIGINT NOT NULL REFERENCES grid,
    UNIQUE (grid_id, number)
);

CREATE TABLE grid_cell (
    grid_column_id BIGINT NOT NULL REFERENCES grid_column,
    grid_row_id BIGINT NOT NULL REFERENCES grid_row,
    value TEXT NOT NULL,
    PRIMARY KEY (grid_column_id, grid_row_id)
);