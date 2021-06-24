ALTER TABLE log
    add timestamp timestamptz NOT NULL DEFAULT now();
