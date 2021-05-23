CREATE OR REPLACE FUNCTION update_modified_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.modified = now();
    RETURN NEW;
END;
$$ language 'plpgsql';

/* wall */
CREATE TABLE wall
(
    id          BIGINT NOT NULL PRIMARY KEY,
    active      BOOLEAN,
    initialized BOOLEAN,
    sections    TEXT   NOT NULL,

    created     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    modified    TIMESTAMP WITHOUT TIME ZONE
);

CREATE TRIGGER wall_modified
    BEFORE UPDATE
    ON wall
    FOR EACH ROW
EXECUTE PROCEDURE update_modified_column();

/* wall entry */
CREATE TABLE wall_entry
(
    id                 VARCHAR(255)                NOT NULL PRIMARY KEY,
    type               VARCHAR(255)                NOT NULL,

    headline           VARCHAR(255)                NOT NULL,
    subject            TEXT                        NOT NULL,
    body               TEXT                        NOT NULL,
    permalink          VARCHAR(255)                NOT NULL,
    attachment         TEXT                        NOT NULL,

    author             BIGINT                      NOT NULL,

    created_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    content_updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,

    latitude           double precision,
    longitude          double precision,

    wall_section_id    BIGINT                      NOT NULL,

    wall_id            BIGINT                      NOT NULL,

    created            TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    modified           TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT fk_wall_entry_wall FOREIGN KEY (wall_id)
        REFERENCES wall (id) MATCH SIMPLE
);

CREATE TRIGGER wall_entry_modified
    BEFORE UPDATE
    ON wall_entry
    FOR EACH ROW
EXECUTE PROCEDURE update_modified_column();

/* wall page */
CREATE SEQUENCE wall_page_seq;
CREATE TABLE wall_page
(
    id       bigint       not null primary key default nextval('wall_page_seq'::regclass),
    next     VARCHAR(255) NOT NULL,
    etag     VARCHAR(255) NOT NULL,

    created  TIMESTAMP WITHOUT TIME ZONE       DEFAULT NOW(),
    modified TIMESTAMP WITHOUT TIME ZONE
);

CREATE TRIGGER wall_page_modified
    BEFORE UPDATE
    ON wall_page
    FOR EACH ROW
EXECUTE PROCEDURE update_modified_column();

/* teams wall config */
CREATE SEQUENCE teams_wall_config_seq;
CREATE TABLE teams_wall_config
(
    id       bigint  not null primary key default nextval('teams_wall_config_seq'::regclass),
    active   BOOLEAN NOT NULL,
    url      TEXT    NOT NULL,

    wall_id  BIGINT  NOT NULL,

    created  TIMESTAMP WITHOUT TIME ZONE  DEFAULT NOW(),
    modified TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT fk_teams_wall_config_wall FOREIGN KEY (wall_id)
        REFERENCES wall (id) MATCH SIMPLE
);

CREATE TRIGGER teams_wall_config_modified
    BEFORE UPDATE
    ON teams_wall_config
    FOR EACH ROW
EXECUTE PROCEDURE update_modified_column();

/* wall sniff entry */
CREATE SEQUENCE wall_sniff_entry_seq;
CREATE TABLE wall_sniff_entry
(
    id      bigint not null primary key default nextval('wall_sniff_entry_seq'::regclass),
    wall_id BIGINT NOT NULL,
    link    VARCHAR(255),
    author  VARCHAR(255)
);

CREATE TRIGGER wall_sniff_entry_modified
    BEFORE UPDATE
    ON wall_sniff_entry
    FOR EACH ROW
EXECUTE PROCEDURE update_modified_column();

/* wall entry sniff entry */
CREATE TABLE wall_entry_sniff_entry
(
    id                  VARCHAR(255)                NOT NULL PRIMARY KEY,
    type                VARCHAR(255)                NOT NULL,

    headline            VARCHAR(255)                NOT NULL,
    subject             TEXT                        NOT NULL,
    body                TEXT                        NOT NULL,
    permalink           VARCHAR(255)                NOT NULL,
    attachment          TEXT                        NOT NULL,

    author              BIGINT                      NOT NULL,

    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    content_updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,

    latitude            double precision,
    longitude           double precision,

    wall_section_id     BIGINT                      NOT NULL,

    wall_id             BIGINT                      NOT NULL,
    wall_sniff_entry_id BIGINT                      NOT NULL
);
