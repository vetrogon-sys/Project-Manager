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