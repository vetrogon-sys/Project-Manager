import React from 'react';
import { Typography, Avatar } from '@mui/material';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField } from '@mui/material';
import { LocalizationProvider, DatePicker } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import dayjs from 'dayjs';
import { List, ListItem, ListItemAvatar, ListItemText } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import PlusIcon from '@mui/icons-material/Add';
import CrossIcon from '@mui/icons-material/Remove';
import projectController from '../../services/ProjectController';
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

export default function CreateTask(desk, taskErrors, _clouseDialog, _setTaskErrors) {
    let title;
    let description;
    let reqResolutionDate;
    let errors = {
        titleMessage: null,
        descriptionMessage: null,
        reqResolutionDateMessage: null,
    };

    const createTask = async (taskDto) => {
        return await createTaskInDesk(desk.id, taskDto);
    }

    const handleTitleInput = (event) => {
        title = event.target.value;
    }

    const handleDescriptionInput = (event) => {
        description = event.target.value;
    }

    const acceptChanges = async () => {
        const titleReg = new RegExp('(?=.{15,125}$)');
        if (!titleReg.test(title)) {
            errors.titleMessage = 'Task title must be between 15 and 125 symbols';
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
            errors.titleMessage = response.errors.title;
            errors.descriptionMessage = response.errors.description;
            errors.reqResolutionDateMessage = response.errors.reqResolutionDate;
            _setTaskErrors(errors);
        } else {
            _clouseDialog();
        }
    }

    return (
        <Dialog
            open={true}
            onClose={_clouseDialog}
            fullWidth={true}
            sx={{
                minWidth: '60rem',
                backgroundColor: 'rgb(255, 255, 255, .2)',
                boxShadow: 'none',
            }}>
            <DialogTitle>Create task in `{desk ? desk.name : ''}` Desk</DialogTitle>
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
                    // label="Multiline"
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
                {/* <Button variant="contained" color="error" onClick={cancelDialog}>Cancel</Button> */}
            </DialogActions>
        </Dialog>
    );
}