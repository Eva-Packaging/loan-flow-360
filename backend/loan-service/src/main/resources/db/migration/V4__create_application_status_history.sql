CREATE TABLE application_status_history (
                                            status_history_id BIGINT PRIMARY KEY AUTO_INCREMENT,

                                            application_id BIGINT NOT NULL,

                                            previous_status VARCHAR(50),
                                            new_status VARCHAR(50) NOT NULL,

                                            changed_by_user_id BIGINT NOT NULL,
                                            change_reason VARCHAR(255),

                                            changed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                            CONSTRAINT fk_application_status_history
                                                FOREIGN KEY (application_id)
                                                    REFERENCES loan_application(application_id)
);