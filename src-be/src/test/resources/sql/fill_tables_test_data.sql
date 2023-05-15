--DELETE TASKS CASCADE;
--DELETE DESKS CASCADE;
--DELETE authority_opportunities CASCADE;
--DELETE authorities CASCADE;
--DELETE security_opportunities CASCADE;
--DELETE PROJECTS CASCADE;
--DELETE USERS CASCADE;

INSERT INTO USERS (id, email, first_name, last_name)
    VALUES (1, 'user_test@gmail.com', 'Adam', 'Green');

INSERT INTO PROJECTS (id, name, creator_id)
    VALUES (1, 'ProjectManager', 1),
           (2, 'ProjectManager_v2', 1);

INSERT INTO DESKS (id, name, project_id)
    VALUES (1, 'Discus', 1),
           (2, 'Development', 1),
           (3, 'To Test', 1),
           (4, 'Discus', 2);

INSERT INTO TASKS (id, title, desk_id)
    VALUES (1, 'Tested task 1', 1),
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

insert into security_opportunities (opportunity)
    values ('Create'),
           ('Delete');

insert into authorities (id, signature, assigned_user_id, related_project_id)
    values (1, 'user_1_project_1_Create_Delete', 1, 1),
           (2, 'user_1_project_2_Create', 1, 2);

insert into authority_opportunities (authority_id, opportunity_id)
    values (1, 'Create'),
           (1, 'Delete'),
           (2, 'Create');