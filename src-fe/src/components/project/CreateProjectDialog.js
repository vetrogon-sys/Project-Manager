import React, { useState } from 'react';
import { Typography, Avatar } from '@mui/material';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField } from '@mui/material';
import { LocalizationProvider, DatePicker } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import dayjs from 'dayjs';
import projectController from '../../services/ProjectController';

async function saveProject(projectDto) {
    const data = await projectController().create(projectDto)
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });

    return {
        task: data,
    };
}

export default function CreateProject(props) {
    const [title, setTitle] = useState(null);
    const [description, setDescription] = useState(null);

    const handleTitleInput = (event) => {
        setTitle(event.target.value);
    }

    const handleDescriptionInput = (event) => {
        setDescription(event.target.value);
    }

    const cancelDialog = () => {
        props.closeDialog();
    }

    const acceptChanges = async () => {

        const projectDto = {
            name: title,
            description: description,
        };

        await saveProject(projectDto);
        props.closeDialog();

    }

    return (
        <Dialog
            open={true}
            onClose={props.closeDialog}
            fullWidth={true}
            sx={{
                minWidth: '60rem',
                backgroundColor: 'rgb(255, 255, 255, .2)',
                boxShadow: 'none',
            }}>
            <DialogTitle>Create new project</DialogTitle>
            <DialogContent>
                <Typography>Name:</Typography>
                <TextField
                    fullWidth={true}
                    onInput={handleTitleInput}
                />
                <Typography>Description:</Typography>
                <TextField
                    multiline
                    fullWidth={true}
                    rows={5}
                    onInput={handleDescriptionInput}
                />
            </DialogContent>
            <DialogActions>
                <Button variant="contained" color="success" onClick={acceptChanges}>Create</Button>
                <Button variant="contained" color="error" onClick={cancelDialog}>Cancel</Button>
            </DialogActions>
        </Dialog>
    );
}