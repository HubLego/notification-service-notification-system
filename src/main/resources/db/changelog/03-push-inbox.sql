CREATE TABLE IF NOT EXISTS push_inbox
(
    id         UUID PRIMARY KEY        NOT NULL,
    created_at TIMESTAMP DEFAULT NOW() NOT NULL,
    topic      VARCHAR(255)            NOT NULL,
    key        VARCHAR(255)            NOT NULL,
    value      TEXT                    NOT NULL,
    processed  BOOLEAN   DEFAULT FALSE NOT NULL,
    attempt    INTEGER   DEFAULT 1     NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uq_push_key_value ON push_inbox (key, value);
