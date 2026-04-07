CREATE TABLE loan_application_addresses (
    address_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_id  BIGINT NOT NULL,
    address_type    VARCHAR(30) NOT NULL,
    line1           VARCHAR(255) NOT NULL,
    line2           VARCHAR(255) NULL,
    city            VARCHAR(100) NOT NULL,
    state_code      VARCHAR(20) NOT NULL,
    postal_code     VARCHAR(20) NOT NULL,
    country_code    VARCHAR(10) NOT NULL DEFAULT 'CA',

    CONSTRAINT fk_addresses_application
        FOREIGN KEY (application_id) REFERENCES loan_applications(application_id),

    INDEX idx_addresses_application_id (application_id),
    INDEX idx_addresses_application_type (application_id, address_type)
);