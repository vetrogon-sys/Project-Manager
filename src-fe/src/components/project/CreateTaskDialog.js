import React, { useState } from 'react';
import { Typography, Avatar } from '@mui/material';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField } from '@mui/material';
import { LocalizationProvider, DatePicker } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import dayjs from 'dayjs';
import TaskController from '../../services/TaskController';

async function createTaskInDesk(deskId, taskDto) {
    const data = await TaskController(deskId).createTaskInDesk(taskDto)
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

export default function CreateTask(props) {
    const [title, setTitle] = useState(null);
    const [description, setDescription] = useState(null);
    const [reqResolutionDate, setReqResolutionDate] = useState(null);
    const [taskErrors, setTaskErrors] = useState({
        titleMessage: null,
        descriptionMessage: null,
        reqResolutionDateMessage: null,
    });

    const createTask = async (taskDto) => {
        return await createTaskInDesk(props.desk.id, taskDto);
    }

    const handleTitleInput = (event) => {
        setTitle(event.target.value);
    }

    const handleDescriptionInput = (event) => {
        setDescription(event.target.value);
    }

    const cancelDialog = () => {
        props.clouseDialog();
    }

    const acceptChanges = async () => {
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

        if (reqResolutionDate && !reqResolutionDate.isAfter(dayjs())) {
            taskErrors.reqResolutionDateMessage = 'You can chose only day in future to resolute this task';
        } else {
            taskErrors.reqResolutionDateMessage = null;
        }
        
        if (taskErrors.titleMessage
            || taskErrors.descriptionMessage
            || taskErrors.reqResolutionDateMessage) {
            return;
        }

        const taskDto = {
            title: title,
            description: description,
            creationDate: dayjs().format('YYYY-MM-DD'),
            reqResolutionDate: reqResolutionDate ? reqResolutionDate.format('YYYY-MM-DD') : null,
        };

        const response = await createTask(taskDto);
        if (response.errors) {
            let errors = {};
            errors.titleMessage = response.errors.title;
            errors.descriptionMessage = response.errors.description;
            errors.reqResolutionDateMessage = response.errors.reqResolutionDate;
            setTaskErrors(errors);
        } else {
            props._clouseDialog();
        }
    }

    return (
        <Dialog
            open={true}
            onClose={props._clouseDialog}
            fullWidth={true}
            sx={{
                minWidth: '60rem',
                backgroundColor: 'rgb(255, 255, 255, .2)',
                boxShadow: 'none',
            }}>
            <DialogTitle>Create task in `{props.desk ? props.desk.name : ''}` Desk</DialogTitle>
            <DialogContent>
                <Typography>Title:</Typography>
                <TextField
                    fullWidth={true}
                    onInput={handleTitleInput}
                    error={taskErrors && taskErrors.titleMessage ? true : false}
                    helperText={taskErrors ? taskErrors.titleMessage : ''}
                />
                <Typography>Description:</Typography>
                <TextField
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
                        onChange={(newValue) => setReqResolutionDate(newValue)}
                        slotProps={{
                            textField: {
                                error: taskErrors && taskErrors.reqResolutionDateMessage ? true : false,
                                helperText: taskErrors ? taskErrors.reqResolutionDateMessage : '',
                            },
                        }}
                    />
                </LocalizationProvider>
            </DialogContent>
            <DialogActions>
                <Button variant="contained" color="success" onClick={acceptChanges}>Accept</Button>
                <Button variant="contained" color="error" onClick={cancelDialog}>Cancel</Button>
            </DialogActions>
        </Dialog>
    );
}