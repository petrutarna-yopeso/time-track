CREATE TABLE IF NOT EXISTS projects
(
    id bigint GENERATED ALWAYS AS IDENTITY,
    name varchar NOT NULL,
    description varchar,
    CONSTRAINT projects_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS employees
(
    id int GENERATED ALWAYS AS IDENTITY,
    name varchar NOT NULL,
    surname varchar NOT NULL,
    email varchar NOT NULL,
    CONSTRAINT employees_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS time_trackings
(
    id int GENERATED ALWAYS AS IDENTITY,
    day DATE NOT NULL,
    duration TIME NOT NULL,
    description varchar,
    employee_id int NOT NULL,
    project_id int NOT NULL,
    CONSTRAINT time_trackings_pkey PRIMARY KEY (id),
    CONSTRAINT FK_Employee FOREIGN KEY (employee_id) REFERENCES employees(id),
    CONSTRAINT FK_Project FOREIGN KEY (project_id) REFERENCES projects(id)
)