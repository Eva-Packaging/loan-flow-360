CREATE TABLE application_employment_details (
    employment_detail_id  BIGINT         AUTO_INCREMENT PRIMARY KEY,
    application_id        BIGINT         NOT NULL UNIQUE,
    employer_name         VARCHAR(255)   NOT NULL,
    job_title             VARCHAR(150)   NOT NULL,
    employment_type       VARCHAR(50)    NOT NULL,
    monthly_income        DECIMAL(15,2)  NOT NULL,
    years_employed        DECIMAL(4,1)   NOT NULL,

    CONSTRAINT fk_employment_application
        FOREIGN KEY (application_id) REFERENCES loan_applications(application_id),

    CONSTRAINT uq_employment_application
        UNIQUE (application_id)
);