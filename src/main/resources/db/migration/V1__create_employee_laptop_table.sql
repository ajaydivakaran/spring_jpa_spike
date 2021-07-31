CREATE TABLE employee
(
    id uuid PRIMARY KEY,
    name text,
    laptop_id uuid
);

CREATE TABLE laptop
(
    id uuid PRIMARY KEY ,
    model text
);
