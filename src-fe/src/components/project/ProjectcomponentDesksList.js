import React, { useState, useEffect } from 'react';
import { Box, Typography, Card, CardContent, CardActions, Button, IconButton, Avatar, List, ListItem } from '@mui/material';
import PlusIcon from '@mui/icons-material/Add';
import AddIcon from '@mui/icons-material/Add.js';
import MoreIcon from '@mui/icons-material/ReadMore.js';
import DeleteIcon from '@mui/icons-material/Delete.js';
import EditTaskDialog from './EditTaskDialog.js';
import deskController from '../../services/DeskController';
import taskController from '../../services/TaskController';
import userController from '../../services/UserController.js';
import CreateTaskDialog from './CreateTaskDialog.js';
import CreateDeskDialog from './CreateDeskDialog.js';
import DeleteDeskDialog from './DeleteDeskDialog.js';

async function getDesks(projectId, setIsLoading) {
    if (setIsLoading) {
        setIsLoading(true);
    }

    const data = await deskController(projectId).findAll()
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });

    if (setIsLoading) {
        setIsLoading(false);
    }

    return {
        desks: data,
    };

}

async function isAnyUserAssignToTaskWithId(taskId) {
    const data = await userController().isAssignedToTasktWithIdExist(taskId)
        .then((response) => {
            return response.status === 204 ? false : true;
        })
        .catch((err) => {
            return err.response;
        });

    return {
        isExist: data,
    };
}

async function getUserAssignedToTaskWithId(taskId) {
    const data = await userController().findAssignedToTasktWithId(taskId)
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return null;
        });

    return {
        user: data,
    };
}

async function getTasksForDeskWithId(deskId, setIsLoading) {
    if (setIsLoading) {
        setIsLoading(true);
    }

    const data = await taskController(deskId).findAllInDesk()
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });

    if (setIsLoading) {
        setIsLoading(false);
    }

    return {
        tasks: data,
    };

}

async function moveTaskToAnotherDesk(taskId, currentDeskId, updatedDeskId) {
    await taskController(currentDeskId).moveTaskToAnotherDesk(taskId, updatedDeskId);
}

export default function DesksList(projectId, projectAssignedUsers, _setLoading) {
    const [desks, setDesks] = useState();
    const [tasks, setTasks] = useState(new Map());
    const [assignedUsers, setAssignedUsers] = useState(new Map());
    const [isCreateTaskDialogOpen, setIsCreateTaskDialogOpen] = useState(false);
    const [isEditTaskDialogOpen, setIsEditTaskDialogOpen] = useState(false);
    const [isCreateDeskMode, setIsCreateDeskMode] = useState(false);
    const [isDeleteDeskDialogOpen, setIsDeleteDeskDialogOpen] = useState(false);
    const [editedTask, setEditedTask] = useState(null);
    const [deskToChange, setDeskToChange] = useState(null);

    useEffect(() => {
        const fetchData = async (isLoading) => {
            let response = await getDesks(projectId, isLoading ? _setLoading : null);

            if (response.desks) {

                response.desks.forEach(async (desk) => {
                    let responseTasks = (await getTasksForDeskWithId(desk.id, isLoading ? _setLoading : null)).tasks;

                    responseTasks.forEach(async (task) => {
                        const isAssignUserExist = (await isAnyUserAssignToTaskWithId(task.id)).isExist;
                        if (isAssignUserExist) {
                            const user = (await getUserAssignedToTaskWithId(task.id)).user;
                            setAssignedUsers(new Map(assignedUsers.set(task.id, user)))
                        }
                    })
                    setTasks(new Map(tasks.set(desk.name, responseTasks)));
                })

                setDesks(response.desks);
            }
        };

        fetchData(true);

        const interval = setInterval(() => {
            fetchData(false);
        }, 100000000)
        return () => {
            clearInterval(interval);
        }

    }, [])

    useEffect(() => {
        const fetchData = async () => {
            desks.forEach(async (desk) => {
                let responseTasks = (await getTasksForDeskWithId(desk.id, null)).tasks;
                responseTasks.forEach(async (task) => {
                    const isAssignUserExist = (await isAnyUserAssignToTaskWithId(task.id)).isExist;
                    if (isAssignUserExist) {
                        const user = (await getUserAssignedToTaskWithId(task.id)).user;
                        setAssignedUsers(new Map(assignedUsers.set(task.id, user)))
                    }
                })
                setTasks(new Map(tasks.set(desk.name, responseTasks)));
            });
        };

        if (desks) {
            fetchData();
        }
    }, [isCreateTaskDialogOpen, isEditTaskDialogOpen, isCreateDeskMode, isDeleteDeskDialogOpen])

    const getDeskByName = (name) => {
        return desks.find(desk => desk.name === name);
    }

    const openCreateTaskDialog = (desk) => {
        setDeskToChange(desk);
        setIsCreateTaskDialogOpen(true);
    }

    const closeCreateTaskDialog = () => {
        setIsCreateTaskDialogOpen(false);
    }

    const openDeleteDeskDialog = (desk) => {
        setDeskToChange(desk);
        setIsDeleteDeskDialogOpen(true);
    }

    const closeDeleteDeskDialog = () => {
        setIsDeleteDeskDialogOpen(false);
    }

    const onepnEditTaskDialogForTask = (event, task) => {
        setDeskToChange(getDeskByName(event.currentTarget.parentElement.parentElement.parentElement.parentElement.id));
        setEditedTask(task);
        setIsEditTaskDialogOpen(true);
    }

    const closeEditTaskDialog = () => {
        setIsEditTaskDialogOpen(false);
    }

    const openCreateDeskDialog = () => {
        setIsCreateDeskMode(true);
    }

    const closeCreateDeskDialog = () => {
        setIsCreateDeskMode(false);
    }

    const onDragStart = (evt) => {
        let element = evt.currentTarget;
        element.classList.add("dragged");
        evt.dataTransfer.setData("taskId", element.id);
        evt.dataTransfer.setData("currentDeskName", element.parentElement.parentElement.id);
        evt.dataTransfer.effectAllowed = "move";
    };

    const onDragEnd = (evt) => {
        evt.currentTarget.classList.remove("dragged");
    };

    const onDragEnter = (evt) => {
        evt.preventDefault();
        let element = evt.currentTarget;
        element.classList.add("dragged-over");
        evt.dataTransfer.dropEffect = "move";
    };

    const onDragLeave = (evt) => {
        let currentTarget = evt.currentTarget;
        let newTarget = evt.relatedTarget;
        if (newTarget.parentNode === currentTarget || newTarget === currentTarget)
            return;
        evt.preventDefault();
        let element = evt.currentTarget;
        element.classList.remove("dragged-over");
    };

    const onDragOver = (evt) => {
        evt.preventDefault();
        evt.dataTransfer.dropEffect = "move";
    };

    const onDrop = (evt, deskName) => {
        evt.preventDefault();
        evt.currentTarget.classList.remove("dragged-over");
        let taskId = evt.dataTransfer.getData("taskId");
        let currentDeskName = evt.dataTransfer.getData("currentDeskName");

        let currentDeskTasks = tasks.get(currentDeskName);
        const taskToMove = currentDeskTasks.find(task => taskId === task.id.toString());
        const currentDeskUpdatedTasks = currentDeskTasks.filter(task => taskToMove !== task);
        let updatedTasks = new Map(tasks.set(currentDeskName, currentDeskUpdatedTasks));

        let updatedDeskTasks = tasks.get(deskName);
        updatedDeskTasks.push(taskToMove);

        const currentDeskId = getDeskByName(currentDeskName).id;
        const updatedDeskId = getDeskByName(deskName).id;
        moveTaskToAnotherDesk(taskId, currentDeskId, updatedDeskId);
        setTasks(new Map(updatedTasks.set(deskName, updatedDeskTasks)));
    };

    const getAvatarForUser = (user) => {
        return (
            <Avatar
                key={user.id}
                alt={user.firstName + ' ' + user.lastName}
                src={user.imgUrl}
                sx={{
                    width: 30,
                    height: 30,
                    fontSize: 12
                }} >
                {user.firstName ? (user.firstName.charAt(0) + ' ' + user.lastName.charAt(0)) : ''}
            </Avatar>
        )
    }

    const getTaskElement = (task) => {
        return (
            <Card
                key={task.id}
                id={task.id}
                sx={{
                    textAlign: 'left',
                    margin: '1rem 0',
                }}
                draggable
                onDragStart={(e) => onDragStart(e)}
                onDragEnd={(e) => onDragEnd(e)}>
                <CardContent sx={{
                    padding: '.5rem .5rem 0 .5rem'
                }}>
                    <Typography sx={{ fontSize: 16 }} component="div" >
                        {task.title}
                    </Typography>
                    <Typography sx={{ fontSize: 14 }} color="text.secondary">
                        {task.description
                            ? (task.description.substring(0, 32) + (task.description.length > 32 ? '...' : ''))
                            : 'There isn\'t descritpion here...'}
                    </Typography>
                </CardContent>
                <CardActions sx={{
                    padding: '.1rem .5rem',
                    display: 'flex',
                    justifyContent: 'space-between'
                }}>
                    <IconButton
                        className='lorn-more-ico-button'
                        onClick={e => onepnEditTaskDialogForTask(e, task)}
                        size='small'>
                        <MoreIcon />
                    </IconButton>
                    {assignedUsers.get(task.id)
                        ? getAvatarForUser(assignedUsers.get(task.id))
                        : <div></div>}
                </CardActions>
            </Card>
        )
    }

    const getDeskTasks = (desk) => {
        if (tasks.size === 0) {
            return <div></div>
        }

        const tasksList = tasks.get(desk.name);
        if (!tasksList) {
            return <div></div>
        }

        return tasksList
            .map(task => getTaskElement(task));
    }

    const getDeskElement = (desk) => {

        return (
            <ListItem
                key={desk.id}
                sx={{
                    alignItems: 'self-start',
                    paddingLeft: 0,
                    paddingRight: 0
                }}>
                <Box
                    key={desk.id}
                    id={desk.name}
                    sx={{
                        width: '16rem',
                        margin: '.7rem',
                        backgroundColor: 'rgb(245, 247, 252)',
                        height: 'fit-content',
                        maxHeight: '100%',
                        borderRadius: '.3rem',
                        display: 'flex',
                        flexDirection: 'column'
                    }
                    }
                    onDragLeave={(e) => onDragLeave(e)}
                    onDragEnter={(e) => onDragEnter(e)}
                    onDragEnd={(e) => onDragEnd(e)}
                    onDragOver={(e) => onDragOver(e)}
                    onDrop={(e) => onDrop(e, desk.name)}
                >
                    <Box
                        sx={{
                            display: 'flex',
                            alignItems: 'center',
                            width: '100%',
                            backgroundColor: 'rgb(199, 208, 235)',
                            minHeight: '3rem',
                            fontWeight: 400
                        }}
                    >
                        <div style={{
                            marginLeft: '1rem'
                        }}>
                            {desk.name}
                        </div>
                        <IconButton
                            onClick={e => openDeleteDeskDialog(desk)}
                            sx={{
                                marginLeft: 'auto',
                            }}>
                            <DeleteIcon />
                        </IconButton>
                    </Box>
                    <Box sx={{
                        margin: '.5rem',
                        overflow: "auto",
                        height: '100%'
                    }}>
                        {getDeskTasks(desk)}
                    </Box>
                    <Box sx={{
                        alignSelf: 'flex-end',
                    }}>
                        <Button
                            onClick={e => openCreateTaskDialog(desk)}
                            sx={{
                                margin: '.5rem',
                            }}>
                            <PlusIcon />
                            Create task
                        </Button>
                    </Box>

                    {isCreateTaskDialogOpen
                        ? <CreateTaskDialog
                            desk={deskToChange}
                            clouseDialog={closeCreateTaskDialog} />
                        : null}
                    {isEditTaskDialogOpen
                        ? <EditTaskDialog
                            desk={deskToChange}
                            editedTask={editedTask}
                            clouseDialog={closeEditTaskDialog}
                            assignedUser={assignedUsers.get(editedTask.id)}
                            projectAssignedUsers={projectAssignedUsers} />
                        : null}
                    {isDeleteDeskDialogOpen
                        ? <DeleteDeskDialog
                            projectId={projectId}
                            desk={deskToChange}
                            closeDialog={closeDeleteDeskDialog} />
                        : null}
                </Box >
            </ListItem>
        )
    }

    const getCreateDeskButton = () => {
        if (isCreateDeskMode) {
            return <CreateDeskDialog
                projectId={projectId}
                closeCreateMenu={closeCreateDeskDialog} />
        }
        return (
            <Button onClick={openCreateDeskDialog}>
                <AddIcon />
                Create desk
            </Button>
        )
    }

    const getDesksContainers = () => {
        if (!desks) {
            return <div></div>;
        }

        return (
            <Box sx={{
                display: 'flex',
                flexDirection: 'row',
                width: '95%',
                marginLeft: 0,
                position: 'relative'
            }}>
                <List sx={{
                    display: 'flex',
                    flexDirection: 'row',
                    alignContent: 'flex-start',
                    justifyContent: 'flex-start',
                }}>
                    {desks.map((desk) => getDeskElement(desk))}
                    <ListItem
                        key={-1}
                        sx={{
                            alignItems: 'self-start'
                        }}>
                        <Box sx={{
                            width: '15rem',
                            margin: '.7rem',
                            backgroundColor: 'rgb(245, 247, 252)',
                            height: 'fit-content',
                            maxHeight: '100%',
                            borderRadius: '.3rem',
                            display: 'flex',
                            flexDirection: 'column'
                        }}>
                            {getCreateDeskButton()}
                        </Box>
                    </ListItem>
                </List>
            </Box >
        )
    }

    return (
        <div style={{
            marginTop: '1rem',
            width: '100%',
            display: 'flex',
            justifyContent: 'flex-start',
        }}>
            {getDesksContainers()}
        </div>
    );

}