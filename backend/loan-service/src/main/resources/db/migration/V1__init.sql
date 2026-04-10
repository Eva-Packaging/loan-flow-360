CREATE TABLE loan_application (
    application_id BIGINT PRIMARY KEY AUTO_INCREMENT,

    application_reference VARCHAR(255) NOT NULL UNIQUE,
    applicant_user_id BIGINT NOT NULL,
    assigned_officer_user_id BIGINT,

    loan_type VARCHAR(50) NOT NULL,
    requested_amount DECIMAL(15,2) NOT NULL,
    term_months INT NOT NULL,
    annual_income DECIMAL(15,2) NOT NULL,

    employment_status VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,

    submitted_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);