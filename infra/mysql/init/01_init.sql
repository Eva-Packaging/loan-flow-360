CREATE DATABASE IF NOT EXISTS loanflow_underwriting_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
GRANT ALL PRIVILEGES ON loanflow_underwriting_db.* TO '${DATABASE_USER}'@'%';
FLUSH PRIVILEGES;

