import React, { useState, useEffect } from 'react';
import { Typography, Avatar } from '@mui/material';
import { Dialog, DialogTitle, DialogContent, Box, DialogActions, TextField, Button } from '@mui/material';
import { List, ListItem, ListItemAvatar, ListItemText } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import DeleteIcon from '@mui/icons-material/Delete';
import CrossIcon from '@mui/icons-material/Remove';

export default function ProjectEditDialog(project, assignedUsers, usersToRemove, _cancelDialog, _updateProject, _setUsersToRemove, _updateProjectDescription) {
    let updDescription = '';

    const getAvatarForUser = (user) => {
        return (
            <Avatar alt={user.firstName + ' ' + user.lastName} src={user.imgUrl} >
                {user.firstName.charAt(0) + ' ' + user.lastName.charAt(0)}
            </Avatar>
        )
    }

    const acceptChanges = () => {
        _updateProjectDescription(updDescription);

        _updateProject();
    }

    const cancelDialog = () => {
        _setUsersToRemove(new Array());
        updDescription = '';

        _cancelDialog();
    }

    const removeUser = (user) => {
        _setUsersToRemove(current => [...current, user])
    }

    const removeUserFromRemoveList = (user) => {
        _setUsersToRemove(current => current.filter(u => u !== user))
    }

    const handleDescriptionInput = (event) => {
        updDescription = event.target.value;
    }

    const getAssignedUsersListElements = () => {
        if (!assignedUsers) {
            return <div></div>
        }

        return assignedUsers
            .map(user => (
                <ListItem
                    sx={{
                        backgroundColor: usersToRemove.includes(user) ? 'rgb(224, 52, 52);' : '',
                    }}
                    secondaryAction={
                        !usersToRemove.includes(user) ?
                            <IconButton edge="end" aria-label="delete" onClick={() => removeUser(user)}>
                                <DeleteIcon />
                            </IconButton> :
                            <IconButton edge="end" aria-label="delete" onClick={() => removeUserFromRemoveList(user)}>
                                <CrossIcon />
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
            );
    }

    return (
        <Dialog open={true} onClose={_cancelDialog} fullWidth={true} sx={{ minWidth: '60rem' }}>
            <DialogTitle>{project ? project.name : ''}</DialogTitle>
            <DialogContent>
                <Typography>Description:</Typography>
                <TextField
                    id="outlined-multiline-static"
                    // label="Multiline"
                    multiline
                    fullWidth={true}
                    rows={5}
                    defaultValue={project ? project.description : 'asd'}
                    onInput={handleDescriptionInput}
                />
                <Typography sx={{ margin: '5px 0' }} >
                    <span>Assigned users:</span>
                </Typography>
                <List style={{ maxHeight: '10rem', overflow: 'auto' }}>
                    {assignedUsers ? getAssignedUsersListElements() : ''}
                </List>
            </DialogContent>
            <DialogActions>
                <Button variant="contained" color="success" onClick={acceptChanges}>Accept</Button>
                <Button variant="contained" color="error" onClick={cancelDialog}>Cancel</Button>
            </DialogActions>
        </Dialog>
    );

}