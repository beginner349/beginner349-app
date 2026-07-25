CREATE TABLE upload (
    id UUID PRIMARY KEY,
    file_name VARCHAR(255),
    file_size BIGINT,
    part_size BIGINT,
    total_parts INT,
    content_type VARCHAR(255),
    s3_key VARCHAR(255),
    s3_upload_id VARCHAR(255),
    status VARCHAR(20) NOT NULL,    -- INITIATED | COMPLETED | ABORTED
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
