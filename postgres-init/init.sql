INSERT INTO
    USERS (id, email, password, first_name, last_name)
VALUES
    (
        nextval('user_id_sequence'),
        'admin@yopmail.com',
        '$2a$12$dhhXme1dEcXG2zwGyV9GsO9K7svbOhd8Khc.i9vMmLMZoih7yqgqW',
        'Adam',
        'Green'
    ),
    (
        nextval('user_id_sequence'),
        'user1@yopmail.com',
        '$2a$12$dhhXme1dEcXG2zwGyV9GsO9K7svbOhd8Khc.i9vMmLMZoih7yqgqW',
        'Igor',
        'Black'
    ),
    (
        nextval('user_id_sequence'),
        'user2@yopmail.com',
        '$2a$12$dhhXme1dEcXG2zwGyV9GsO9K7svbOhd8Khc.i9vMmLMZoih7yqgqW',
        'Felix',
        'Gray'
    ),
    (
        nextval('user_id_sequence'),
        'user3@yopmail.com',
        '$2a$12$dhhXme1dEcXG2zwGyV9GsO9K7svbOhd8Khc.i9vMmLMZoih7yqgqW',
        'Georg',
        'Brown'
    ),
    (
        nextval('user_id_sequence'),
        'user4@yopmail.com',
        '$2a$12$dhhXme1dEcXG2zwGyV9GsO9K7svbOhd8Khc.i9vMmLMZoih7yqgqW',
        'Oleg',
        'Pink'
    ),
    (
        nextval('user_id_sequence'),
        'user5@yopmail.com',
        '$2a$12$dhhXme1dEcXG2zwGyV9GsO9K7svbOhd8Khc.i9vMmLMZoih7yqgqW',
        'Ivan',
        'White'
    );

INSERT INTO
    PROJECTS (id, name, creator_id)
VALUES
    (nextval('project_id_sequence'), 'Project Manager', 1),
    (nextval('project_id_sequence'), 'FPC-DTP-Doulby', 1),
    (nextval('project_id_sequence'), 'HBC-ASD', 2),
    (nextval('project_id_sequence'), 'NFGO-BJF', 2),
    (nextval('project_id_sequence'), 'CDO-IFC', 3),
    (nextval('project_id_sequence'), 'CNDF-IPM-RTOE', 4);

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
    (nextval('desk_id_sequence'), 'Discus', 1),
    (nextval('desk_id_sequence'), 'Development', 1),
    (nextval('desk_id_sequence'), 'To Test', 1),
    (nextval('desk_id_sequence'), 'Done', 1),
    (nextval('desk_id_sequence'), 'Clouse', 1),
    (nextval('desk_id_sequence'), 'Discus', 2);

INSERT INTO
    TASKS (id, title, desk_id)
VALUES
    (nextval('task_id_sequence'), 'Tested task 1', 1),
    (nextval('task_id_sequence'), 'Tested task 2', 1),
    (nextval('task_id_sequence'), 'Tested task 3', 1),
    (nextval('task_id_sequence'), 'Tested task 4', 2),
    (nextval('task_id_sequence'), 'Tested task 5', 2),
    (nextval('task_id_sequence'), 'Tested task 6', 2),
    (nextval('task_id_sequence'), 'Tested task 7', 2),
    (nextval('task_id_sequence'), 'Tested task 8', 2),
    (nextval('task_id_sequence'), 'Tested task 9', 3),
    (nextval('task_id_sequence'), 'Tested task 10', 3),
    (nextval('task_id_sequence'), 'Tested task 11', 4),
    (nextval('task_id_sequence'), 'Tested task 12', 4),
    (nextval('task_id_sequence'), 'Tested task 13', 4),
    (nextval('task_id_sequence'), 'Tested task 14', 4);
