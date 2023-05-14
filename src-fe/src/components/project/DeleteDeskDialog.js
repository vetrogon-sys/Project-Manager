import React, { useState } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, DialogContentText } from '@mui/material';
import deskController from '../../services/DeskController';

async function deleteDeskFromProject(projectId, deskId) {
    const data = await deskController(projectId).deleteById(deskId)
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
}

export default function DeleteDesk(props) {

    const deleteDesk = async () => {
        await deleteDeskFromProject(props.projectId, props.desk.id);

        props.closeDialog();
    }

    return (
        <Dialog
            open={true}
            keepMounted
            onClose={props.closeDialog}
            aria-describedby="alert-dialog-slide-description"
            sx={{
                backgroundColor: 'rgb(199, 208, 235, .3)',
            }}
        >
            <DialogTitle>Delete desk {props.desk.name}</DialogTitle>
            <DialogContent>
                <DialogContentText id="alert-dialog-slide-description">
                    You're shore to delete desk `{props.desk.name}`.<br />
                    All tasks will removed with it
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={deleteDesk}>
                    Delete
                </Button>
                <Button onClick={props.closeDialog}>
                    Cancel
                </Button>
            </DialogActions>
        </Dialog>
    );
}