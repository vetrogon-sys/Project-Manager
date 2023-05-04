INSERT INTO
    USERS (id, email, password, first_name, last_name)
VALUES
    (1, 'admin@yopmail.com', '$2a$12$dhhXme1dEcXG2zwGyV9GsO9K7svbOhd8Khc.i9vMmLMZoih7yqgqW', 'Adam', 'Green'),
    (2, 'user1@yopmail.com', '$2a$12$dhhXme1dEcXG2zwGyV9GsO9K7svbOhd8Khc.i9vMmLMZoih7yqgqW', 'Igor', 'Black'),
    (3, 'user2@yopmail.com', '$2a$12$dhhXme1dEcXG2zwGyV9GsO9K7svbOhd8Khc.i9vMmLMZoih7yqgqW', 'Felix', 'Gray'),
    (4, 'user3@yopmail.com', '$2a$12$dhhXme1dEcXG2zwGyV9GsO9K7svbOhd8Khc.i9vMmLMZoih7yqgqW', 'Georg', 'Brown'),
    (5, 'user4@yopmail.com', '$2a$12$dhhXme1dEcXG2zwGyV9GsO9K7svbOhd8Khc.i9vMmLMZoih7yqgqW', 'Oleg', 'Pink'),
    (6, 'user5@yopmail.com', '$2a$12$dhhXme1dEcXG2zwGyV9GsO9K7svbOhd8Khc.i9vMmLMZoih7yqgqW', 'Ivan', 'White');

INSERT INTO
    PROJECTS (id, name, creator_id)
VALUES
    (1, 'Project Manager', 1),
    (2, 'FPC-DTP-Doulby', 1),
    (3, 'HBC-ASD', 2),
    (4, 'NFGO-BJF', 2),
    (5, 'CDO-IFC', 3),
    (6, 'CNDF-IPM-RTOE', 4);

INSERT INTO
    USER_PROJECTS (project_id, user_id)
VALUES
    (1, 2),
    (1, 4),
    (1, 5),
    (2, 3),
    (2, 5),
    (3, 4),
    (5, 1),
    (5, 2),
    (5, 5),
    (6, 3);

INSERT INTO
    DESKS (id, name, project_id)
VALUES
    (1, 'Discus', 1),
    (2, 'Development', 1),
    (3, 'To Test', 1),
    (4, 'Done', 1),
    (5, 'Clouse', 1),
    (7, 'Discus', 2);

INSERT INTO
    TASKS (id, title, desk_id)
VALUES
    (1, 'Tested task 1', 1),
    (2, 'Tested task 2', 1),
    (3, 'Tested task 3', 1),
    (4, 'Tested task 4', 2),
    (5, 'Tested task 5', 2),
    (6, 'Tested task 6', 2),
    (7, 'Tested task 7', 2),
    (8, 'Tested task 8', 2),
    (9, 'Tested task 9', 3),
    (10, 'Tested task 10', 3),
    (11, 'Tested task 11', 4),
    (12, 'Tested task 12', 4),
    (13, 'Tested task 13', 4),
    (14, 'Tested task 14', 4);