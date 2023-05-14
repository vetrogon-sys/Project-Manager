import React, { useState } from 'react';
import { Typography, Avatar } from '@mui/material';
import { Drawer, Box, Button, TextField } from '@mui/material';
import { LocalizationProvider, DatePicker } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import dayjs from 'dayjs';
import TaskController from '../../services/TaskController';

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
    const [taskErrors, setTaskErrors] = useState({
        titleMessage: null,
        descriptionMessage: null,
        reqResolutionDateMessage: null,
    });

    const editTask = async (taskId, taskDto) => {
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
            && reqResolutionDate.isSame(dayjs(editedTask.reqResolutionDate));
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
            reqResolutionDate: reqResolutionDate ? reqResolutionDate.format('YYYY-MM-DD') : null,
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