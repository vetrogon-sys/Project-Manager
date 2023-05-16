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

INSERT INTO
    SECURITY_OPPORTUNITIES (opportunity)
VALUES
    ('get'),
    ('create'),
    ('delete'),
    ('edit');

INSERT INTO
    AUTHORITIES (id, signature, assigned_user_id, related_project_id)
VALUES
    (nextval('authority_id_sequence'), 'user_1_project_1_get_create_delete_edit', 1, 1),
    (nextval('authority_id_sequence'), 'user_1_project_2_get_create_delete_edit', 1, 2),
    (nextval('authority_id_sequence'), 'user_2_project_3_get_create_delete_edit', 2, 3),
    (nextval('authority_id_sequence'), 'user_2_project_4_get_create_delete_edit', 2, 4),
    (nextval('authority_id_sequence'), 'user_3_project_5_get_create_delete_edit', 3, 5),
    (nextval('authority_id_sequence'), 'user_4_project_6_get_create_delete_edit', 4, 6),
    (nextval('authority_id_sequence'), 'user_1_project_5_get_edit', 1, 5),
    (nextval('authority_id_sequence'), 'user_2_project_1_get_edit', 2, 1),
    (nextval('authority_id_sequence'), 'user_2_project_5_get_edit', 2, 5),
    (nextval('authority_id_sequence'), 'user_3_project_2_get_edit', 3, 2),
    (nextval('authority_id_sequence'), 'user_3_project_6_get_edit', 3, 6),
    (nextval('authority_id_sequence'), 'user_4_project_1_get_edit', 4, 1),
    (nextval('authority_id_sequence'), 'user_4_project_3_get_edit', 4, 3),
    (nextval('authority_id_sequence'), 'user_5_project_1_get_edit', 5, 1),
    (nextval('authority_id_sequence'), 'user_5_project_2_get_edit', 5, 2),
    (nextval('authority_id_sequence'), 'user_5_project_5_get_edit', 5, 5);

INSERT INTO 
    AUTHORITY_OPPORTUNITIES (authority_id, opportunity_id)
VALUES
    (1, 'get'),
    (1, 'create'),
    (1, 'delete'),
    (1, 'edit'),
    (2, 'get'),
    (2, 'create'),
    (2, 'delete'),
    (2, 'edit'),
    (3, 'get'),
    (3, 'create'),
    (3, 'delete'),
    (3, 'edit'),
    (4, 'get'),
    (4, 'create'),
    (4, 'delete'),
    (4, 'edit'),
    (5, 'get'),
    (5, 'create'),
    (5, 'delete'),
    (5, 'edit'),
    (6, 'get'),
    (6, 'create'),
    (6, 'edit'),
    (6, 'delete'),
    (7, 'get'),
    (7, 'edit'),
    (8, 'get'),
    (8, 'edit'),
    (9, 'get'),
    (9, 'edit'),
    (10, 'get'),
    (10, 'edit'),
    (11, 'get'),
    (11, 'edit'),
    (12, 'get'),
    (12, 'edit'),
    (13, 'get'),
    (13, 'edit'),
    (14, 'get'),
    (14, 'edit'),
    (15, 'get'),
    (15, 'edit'),
    (16, 'get'),
    (16, 'edit');