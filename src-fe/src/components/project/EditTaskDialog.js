import React, { useState } from 'react';
import { Typography, Avatar, List, ListItem, ListItemAvatar, ListItemText, Drawer, Box, Button, TextField, Stack, IconButton } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove.js';
import { LocalizationProvider, DatePicker } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import dayjs from 'dayjs';
import TaskController from '../../services/TaskController';

async function assignUserToTask(deskId, taskId, userId) {
    await TaskController(deskId).assignUserToTask(taskId, userId)
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });
}

async function unassignUserFromTask(deskId, taskId) {
    await TaskController(deskId).unassignUserFromTask(taskId)
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });
}

async function editTaskInDesk(deskId, taskId, taskDto) {
    const data = await TaskController(deskId).editTaskInDesk(taskId, taskDto)
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });

    if (data.status === 400) {
        return {
            errors: data.data.errors
        }
    }
    return {
        task: data,
    };
}

export default function EditTask(props) {
    const [id, setId] = useState(props.editedTask.id);
    const [title, setTitle] = useState(props.editedTask.title);
    const [description, setDescription] = useState(props.editedTask.description);
    const [reqResolutionDate, setReqResolutionDate] = useState(props.editedTask.reqResolutionDate);
    const [assignedUser, setAssignedUser] = useState(props.assignedUser);
    const [projectAssignedUsers, setProjectAssignedUsers] = useState(props.projectAssignedUsers);
    const [searchMail, setSearchMail] = useState(null);
    const [taskErrors, setTaskErrors] = useState({
        titleMessage: null,
        descriptionMessage: null,
        reqResolutionDateMessage: null,
    });

    const editTask = async (taskId, taskDto) => {
        if (props.assignedUser !== assignedUser) {
            if (assignedUser) {
                assignUserToTask(props.desk.id, taskId, assignedUser.id);
            } else {
                unassignUserFromTask(props.desk.id, taskId);
            }
        }
        return await editTaskInDesk(props.desk.id, taskId, taskDto);
    }

    const handleTitleInput = (event) => {
        setTitle(event.target.value);
    }

    const handleDescriptionInput = (event) => {
        setDescription(event.target.value);
    }

    const isTaskValid = () => {
        const titleReg = new RegExp('(?=.{5,125}$)');
        if (!titleReg.test(title)) {
            taskErrors.titleMessage = 'Task title must be between 5 and 125 symbols';
        } else {
            taskErrors.titleMessage = null;
        }

        if (description && description.length > 512) {
            taskErrors.descriptionMessage = 'Task description must be less than 512 symbols';
        } else {
            taskErrors.descriptionMessage = null;
        }

        if (reqResolutionDate && !dayjs(reqResolutionDate).isAfter(dayjs())) {
            taskErrors.reqResolutionDateMessage = 'You can chose only day in future to resolute this task';
        } else {
            taskErrors.reqResolutionDateMessage = null;
        }

        if (taskErrors.titleMessage
            || taskErrors.descriptionMessage
            || taskErrors.reqResolutionDateMessage) {
            return false;
        }

        return true;
    }

    const isTaskEqualsCurrent = () => {
        const editedTask = props.editedTask;
        return title === editedTask.title
            && description === editedTask.description
            && dayjs(reqResolutionDate).isSame(dayjs(editedTask.reqResolutionDate))
            && assignedUser === props.assignedUser;
    }

    const acceptChanges = async () => {

        if (isTaskEqualsCurrent()) {
            props.clouseDialog();
        }

        if (!isTaskValid()) {
            return;
        }

        const taskDto = {
            title: title,
            description: description,
            creationDate: props.editedTask.creationDate,
            reqResolutionDate: reqResolutionDate ? dayjs(reqResolutionDate).format('YYYY-MM-DD') : null
        };

        const response = await editTask(id, taskDto);
        if (response.errors) {
            let errors = {};
            errors.titleMessage = response.errors.title;
            errors.descriptionMessage = response.errors.description;
            errors.reqResolutionDateMessage = response.errors.reqResolutionDate;
            setTaskErrors(errors);
        } else {
            props.clouseDialog();
        }
    }

    const clouseDialog = () => {
        props.clouseDialog();
    }

    const unassignUser = () => {
        setAssignedUser(null);
    }

    const assignUser = (user) => {
        setAssignedUser(user);
    }

    const getAvatarForUser = (user) => {
        return (
            <Avatar
                key={user.id}
                alt={user.firstName + ' ' + user.lastName}
                src={user.imgUrl}
                sx={{
                    width: 40,
                    height: 40,
                    fontSize: 12
                }} >
                {user.firstName ? (user.firstName.charAt(0) + ' ' + user.lastName.charAt(0)) : ''}
            </Avatar>
        );
    }

    const getAvatarsList = (usersList) => {
        if (!usersList) {
            return <div></div>
        }

        return (<List style={{ maxHeight: '4rem', overflow: 'auto' }}>
            {usersList
                .map((user) => (
                    <ListItem
                        key={user.id}
                        secondaryAction={
                            <IconButton edge="end" aria-label="delete" onClick={() => assignUser(user)}>
                                <AddIcon />
                            </IconButton>
                        }
                    >
                        <ListItemAvatar>
                            {getAvatarForUser(user)}
                        </ListItemAvatar>
                        <ListItemText
                            primary={user.firstName + ' ' + user.lastName}
                            secondary={user.email}
                        />
                    </ListItem>)
                )}
        </List>)
    }

    const getUsersMatchingParameter = () => {
        if (!searchMail) {
            return projectAssignedUsers;
        }
        return projectAssignedUsers.filter(user => user.email.includes(searchMail));
    }

    const getAssignedUserAvatar = () => {
        return (
            <Stack spacing={2} direction="row" alignItems="center">
                <Stack>
                    {getAvatarForUser(assignedUser)}
                </Stack>
                <Stack>
                    <Typography noWrap>{assignedUser.firstName + ' ' + assignedUser.lastName}</Typography>
                    <Typography noWrap variant='subtitle2'>{assignedUser.email}</Typography>
                </Stack>
                <Stack>
                    <IconButton onClick={unassignUser}>
                        <RemoveIcon />
                    </IconButton>
                </Stack>
            </Stack>
        );
    }

    const getAvaliableToAssignUsersElement = () => {

        return (
            <Box>
                <TextField
                    fullWidth={true}
                    label={'some.mail@example.com'}
                    helperText={'Start typing the user\'s email to find'}
                    variant='standard'
                    onInput={event => setSearchMail(event.target.value)} />
                {getAvatarsList(getUsersMatchingParameter())}
            </Box>)
    }

    return (
        <Drawer
            open={true}
            onClose={props.clouseDialog}
            anchor='right'
            sx={{
                backgroundColor: 'rgb(255, 255, 255, .2)',
                boxShadow: 'none',
            }}>
            <Box sx={{
                margin: '1rem',
                marginTop: '6rem',
                width: '40rem',
                display: 'flex',
                flexDirection: 'column'
            }}>
                <Typography
                    variant='h5'
                    sx={{
                        marginBottom: '1rem'
                    }}>
                    Task_{id}
                </Typography>
                <Typography>Title:</Typography>
                <TextField
                    fullWidth={true}
                    defaultValue={title}
                    onInput={e => handleTitleInput(e)}
                    error={taskErrors && taskErrors.titleMessage ? true : false}
                    helperText={taskErrors ? taskErrors.titleMessage : ''}
                />
                <Typography>Description:</Typography>
                <TextField
                    defaultValue={description}
                    multiline
                    fullWidth={true}
                    rows={5}
                    onInput={e => handleDescriptionInput(e)}
                    error={taskErrors && taskErrors.descriptionMessage ? true : false}
                    helperText={taskErrors ? taskErrors.descriptionMessage : ''}
                />
                <Typography>Requaried resolution date:</Typography>
                <LocalizationProvider
                    dateAdapter={AdapterDayjs}
                >
                    <DatePicker
                        onChange={(newValue) => setReqResolutionDate(newValue)}
                        defaultValue={dayjs(reqResolutionDate, 'YYYY-MM-DD')}
                        slotProps={{
                            textField: {
                                error: taskErrors && taskErrors.reqResolutionDateMessage ? true : false,
                                helperText: taskErrors ? taskErrors.reqResolutionDateMessage : '',
                            },
                        }}
                    />
                </LocalizationProvider>
                <Typography>Assigned user:</Typography>
                {assignedUser
                    ? getAssignedUserAvatar()
                    : getAvaliableToAssignUsersElement()}
                <Box sx={{
                    marginTop: '1rem'
                }}>
                    <Button variant="contained" color="success" onClick={acceptChanges}>Accept</Button>
                    <Button variant="contained" color="error" onClick={clouseDialog} sx={{ marginLeft: '1rem' }}>Cancel</Button>
                </Box>
            </Box>
        </Drawer>
    );
}