import React from 'react';
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

export default function EditTask(desk, task, taskErrors, _clouseDialog, _setTaskErrors) {
    let title = task.title;
    let description = task.description;
    let reqResolutionDate = task.reqResolutionDate;
    let errors = {
        titleMessage: null,
        descriptionMessage: null,
        reqResolutionDateMessage: null,
    };

    const editTask = async (taskId, taskDto) => {
        return await editTaskInDesk(desk.id, taskId, taskDto);
    }

    const handleTitleInput = (event) => {
        title = event.target.value;
    }

    const handleDescriptionInput = (event) => {
        description = event.target.value;
    }

    const isTaskValid = () => {
        const titleReg = new RegExp('(?=.{5,125}$)');
        if (!titleReg.test(title)) {
            errors.titleMessage = 'Task title must be between 5 and 125 symbols';
        }

        if (description && description.length > 512) {
            errors.descriptionMessage = 'Task description must be less than 512 symbols';
        }
        if (reqResolutionDate && !reqResolutionDate.isAfter(dayjs())) {
            errors.reqResolutionDateMessage = 'You can chose only day in future to resolute this task';
        }

        if (errors.titleMessage
            || errors.descriptionMessage
            || errors.reqResolutionDateMessage) {
            _setTaskErrors(errors);
            return false;
        }

        return true;
    }

    const isTaskEqualsCurrent = () => {
        return title === task.title
            && description === task.description
            && reqResolutionDate === task.reqResolutionDate;
    }

    const acceptChanges = async () => {

        if (isTaskEqualsCurrent()) {
            _clouseDialog();
        }

        if (!isTaskValid()) {
            return;
        }

        const taskDto = {
            title: title,
            description: description,
            creationDate: task.creationDate,
            reqResolutionDate: reqResolutionDate ? reqResolutionDate.format('YYYY-MM-DD') : null,
        };

        console.log(desk)
        const response = await editTask(task.id, taskDto);
        if (response.errors) {
            errors.titleMessage = response.errors.title;
            errors.descriptionMessage = response.errors.description;
            errors.reqResolutionDateMessage = response.errors.reqResolutionDate;
            _setTaskErrors(errors);
        } else {
            _clouseDialog();
        }
    }

    const clouseDialog = () => {
        _setTaskErrors([]);
        _clouseDialog();
    }

    return (
        <Drawer
            open={true}
            onClose={_clouseDialog}
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
                    Task_{task.id}
                </Typography>
                <Typography>Title:</Typography>
                <TextField
                    fullWidth={true}
                    defaultValue={task.title}
                    onInput={handleTitleInput}
                    error={taskErrors && taskErrors.titleMessage ? true : false}
                    helperText={taskErrors ? taskErrors.titleMessage : ''}
                />
                <Typography>Description:</Typography>
                <TextField
                    defaultValue={task.description}
                    multiline
                    fullWidth={true}
                    rows={5}
                    onInput={handleDescriptionInput}
                    error={taskErrors && taskErrors.descriptionMessage ? true : false}
                    helperText={taskErrors ? taskErrors.descriptionMessage : ''}
                />
                <Typography>Requaried resolution date:</Typography>
                <LocalizationProvider
                    dateAdapter={AdapterDayjs}
                >
                    <DatePicker
                        onChange={(newValue) => reqResolutionDate = newValue}
                        defaultValue={task.reqResolutionDate ? dayjs(task.reqResolutionDate, 'YYYY-MM-DD') : dayjs()}
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