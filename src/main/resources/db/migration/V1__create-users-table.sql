CREATE TABLE users(
    id UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    inCountryId VARCHAR(11) NOT NULL UNIQUE,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);