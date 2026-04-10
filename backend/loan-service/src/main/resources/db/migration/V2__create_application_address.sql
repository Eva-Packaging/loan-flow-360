CREATE TABLE loan_application_addresses (
                                            address_id BIGINT PRIMARY KEY AUTO_INCREMENT,

                                            application_id BIGINT NOT NULL,

                                            address_type VARCHAR(30) NOT NULL,

                                            line1 VARCHAR(255) NOT NULL,
                                            line2 VARCHAR(255),

                                            city VARCHAR(100) NOT NULL,
                                            state_code VARCHAR(20) NOT NULL,
                                            postal_code VARCHAR(20) NOT NULL,
                                            country_code VARCHAR(10) NOT NULL DEFAULT 'CA',

                                            CONSTRAINT fk_loan_application_addresses
                                                FOREIGN KEY (application_id)
                                                    REFERENCES loan_application(application_id)
);