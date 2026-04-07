CREATE TABLE loan_applications (
    application_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_reference    VARCHAR(50)    NOT NULL,
    applicant_user_id        BIGINT         NOT NULL,
    assigned_officer_user_id BIGINT         NULL,
    loan_type                VARCHAR(50)    NOT NULL,
    requested_amount         DECIMAL(15,2)  NOT NULL,
    term_months              INT            NOT NULL,
    annual_income            DECIMAL(15,2)  NOT NULL,
    employment_status        VARCHAR(50)    NOT NULL,
    status                   VARCHAR(50)    NOT NULL,
    submitted_at             TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at               TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_application_reference
        UNIQUE (application_reference),

    INDEX idx_applicant_user_id (applicant_user_id),
    INDEX idx_assigned_officer_user_id (assigned_officer_user_id),
    INDEX idx_status (status),
    INDEX idx_status_submitted_at (status, submitted_at)
);