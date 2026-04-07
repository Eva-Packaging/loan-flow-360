CREATE TABLE application_status_history (
    status_history_id   BIGINT        AUTO_INCREMENT PRIMARY KEY,
    application_id      BIGINT        NOT NULL,
    previous_status     VARCHAR(50)   NULL,
    new_status          VARCHAR(50)   NOT NULL,
    changed_by_user_id  BIGINT        NOT NULL,
    change_reason       VARCHAR(255)  NULL,
    changed_at          TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_status_history_application
        FOREIGN KEY (application_id) REFERENCES loan_applications(application_id),

    INDEX idx_status_history_application_id (application_id),
    INDEX idx_status_history_changed_at (changed_at)
);