INSERT INTO pms_property(id, name)
VALUES (1, 'Cozy room in a vegan oasis');

INSERT INTO pms_property(id, name)
VALUES (2, 'Penthouse in La Juarez');

INSERT INTO pms_property(id, name)
VALUES (3, 'Lamia Fantese near Ostuni');

INSERT INTO pms_property(id, name)
VALUES (4, 'Morus Tiny House : cozy country house on Etna');

INSERT INTO pms_property(id, name)
VALUES (5, 'Olive Tiny House');

CREATE TABLE shedlock
(
    name       VARCHAR(64),
    lock_until TIMESTAMP(3) NULL,
    locked_at  TIMESTAMP(3) NULL,
    locked_by  VARCHAR(255),
    PRIMARY KEY (name)
);
