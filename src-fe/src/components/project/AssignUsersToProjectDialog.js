import React from 'react';
import { Typography, Avatar } from '@mui/material';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button } from '@mui/material';
import { List, ListItem, ListItemAvatar, ListItemText } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import PlusIcon from '@mui/icons-material/Add';
import CrossIcon from '@mui/icons-material/Remove';
import projectController from '../../services/ProjectController';

async function assignUsersByIdsToProjectById(projectId, userIds) {
    await projectController().assignUsers(projectId, userIds);
}

export default function AssignUsersToProjectDialog(project, usersToAssign, unassignedUsers, _cancelDialog, _setUsersToAssign) {

    const assignUser = (user) => {
        _setUsersToAssign(current => [...current, user]);
    }

    const cancelAssigning = (user) => {
        _setUsersToAssign(current => current.filter(u => u !== user));
    }

    const getAvatarForUser = (user) => {
        return (
            <Avatar alt={user.firstName + ' ' + user.lastName} src={user.imgUrl} >
                {user.firstName.charAt(0) + ' ' + user.lastName.charAt(0)}
            </Avatar>
        )
    }

    const acceptChanges = () => {
        const fetchData = async () => {
            await assignUsersByIdsToProjectById(project.id, usersToAssign.map(user => user.id));

            _cancelDialog();
        };

        fetchData();
    }

    const cancelDialog = () => {
        _setUsersToAssign(new Array());
        _cancelDialog();
    }

    const getUnassignedUsersListElements = () => {
        if (!unassignedUsers.users || (unassignedUsers.users && unassignedUsers.users.length === 0)) {
            return <div></div>
        }

        return unassignedUsers.users
            .map(user => (
                <ListItem
                    key={user.id}
                    sx={{
                        backgroundColor: usersToAssign.includes(user) ? 'rgb(76, 224, 113);' : '',
                    }}
                    secondaryAction={
                        !usersToAssign.includes(user) ?
                            <IconButton edge="end" aria-label="delete" onClick={() => assignUser(user)}>
                                <PlusIcon />
                            </IconButton> :
                            <IconButton edge="end" aria-label="delete" onClick={() => cancelAssigning(user)}>
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
                <Typography>Users:</Typography>
                <List style={{ maxHeight: '25rem', overflow: 'auto' }}>
                    {unassignedUsers ? getUnassignedUsersListElements() : ''}
                </List>
            </DialogContent>
            <DialogActions>
                <Button variant="contained" color="success" onClick={acceptChanges}>Accept</Button>
                <Button variant="contained" color="error" onClick={cancelDialog}>Cancel</Button>
            </DialogActions>
        </Dialog>
    );
}