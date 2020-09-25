ALTER TABLE ${flyway:defaultSchema}.wallets
    ADD tenant_id text default '';
ALTER TABLE ${flyway:defaultSchema}.wallets
    ADD "group" text default '';