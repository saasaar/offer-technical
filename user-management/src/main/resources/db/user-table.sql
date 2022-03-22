-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE "user" (
	id uuid DEFAULT random_uuid() PRIMARY KEY,
	username VARCHAR(255) NOT NULL,
	birth_date DATE NOT NULL,
	country VARCHAR(30) NOT NULL,
	phone VARCHAR(13),
	gender VARCHAR(5)
);
COMMENT ON TABLE "user" IS 'Table for storing users';
