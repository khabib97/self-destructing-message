CREATE DATABSE self-destructing-message-db;


CREATE OR REPLACE FUNCTION generate_uid(size INT) RETURNS TEXT AS $$
DECLARE
  characters TEXT := 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  bytes BYTEA := gen_random_bytes(size);
  l INT := length(characters);
  i INT := 0;
  output TEXT := '';
BEGIN
  WHILE i < size LOOP
    output := output || substr(characters, get_byte(bytes, i) % l + 1, 1);
    i := i + 1;
  END LOOP;
  RETURN output;
END;
$$ LANGUAGE plpgsql VOLATILE;

CREATE TABLE notes (
    id TEXT PRIMARY KEY DEFAULT generate_uid(16),
	name VARCHAR(50),
	message TEXT,
	password VARCHAR(64),-- SHA256 256/4=64
	email VARCHAR(50),
	generation_time TIMESTAMP,
	destruction_time TIMESTAMP,
	is_active BOOLEAN
);