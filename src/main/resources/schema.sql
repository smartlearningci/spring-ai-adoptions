CREATE TABLE IF NOT EXISTS dog(
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  owner TEXT,
  description TEXT
);
