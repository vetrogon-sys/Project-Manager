import React, { useState, useEffect } from 'react';
import { Box, AppBar, Toolbar, Typography, AvatarGroup, Avatar } from '@mui/material';
import { Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, TextField, Button } from '@mui/material';
import { List, ListItem, ListItemAvatar, ListItemText } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import AddIcon from '@mui/icons-material/Add';
import DeleteIcon from '@mui/icons-material/Add';
import FolderIcon from '@mui/icons-material/Add';
import projectEditDialog from './ProjectEditDialog';
import projectController from '../../services/ProjectController';
import userController from '../../services/UserController';

async function getAssignedUsers(projectId) {
    const data = await userController().findAssignedToProjectWithId(projectId)
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });

    return {
        usersPage: data,
    };
}

async function getProjectById(projectId) {
    const data = await projectController().findById(projectId)
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });

    return {
        project: data,
    };

}

export default function ProjectComponentHeader(projectId) {
    const [project, setProject] = useState(null);
    const [assignedUsers, setAssignedUsers] = useState(null);
    const [assignedUsersTotalyCount, setAssignedUsersTotalyCount] = useState(0);
    const [usersToRemove, setUsersToRemove] = useState(new Array())
    const [isOpenEditDialog, setIsOpenEditDialog] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            const projectResponse = await getProjectById(projectId);

            setProject(projectResponse.project);

            const assignedUsersResponse = await getAssignedUsers(projectId);

            setAssignedUsers(assignedUsersResponse.usersPage.content);
            setAssignedUsersTotalyCount(assignedUsersResponse.usersPage.totalElements)
        };

        fetchData();

    }, [])

    const getAvatarForUser = (user) => {
        return (
            <Avatar alt={user.firstName + ' ' + user.lastName} src={user.imgUrl} >
                {user.firstName.charAt(0) + ' ' + user.lastName.charAt(0)}
            </Avatar>
        )
    }


    const updateProjectDescription = (updDescription) => {
        setProject({
            ...project,
            description: updDescription
        })
    }

    const updateProject = () => {

        cancelEditProjectDialog();
    }


    const getAssignedUsersAvatars = () => {
        if (!assignedUsers) {
            return <div></div>
        }

        return assignedUsers
            .map(user => getAvatarForUser(user));
    }

    const openEditProjectDialog = () => {
        setIsOpenEditDialog(true);
    }

    const cancelEditProjectDialog = () => {
        setIsOpenEditDialog(false);
    }

    return (
        <div style={{
            height: 35,
            marginTop: '4rem',
            width: '100%',
            opacity: '.7'
        }}>
            <Box sx={{ flexGrow: 1, textAlign: 'left' }}>
                <AppBar position="static">
                    <Toolbar variant="dense">
                        <IconButton onClick={openEditProjectDialog} color='default'>
                            <AddIcon />
                        </IconButton>
                        <Typography variant="h6" color="inherit" component="div" sx={{ flexGrow: 1 }}>
                            {project ? project.name : ''}
                        </Typography>
                        <AvatarGroup max={5}>
                            {assignedUsersTotalyCount ? getAssignedUsersAvatars() : <div></div>}
                        </AvatarGroup>
                    </Toolbar>
                </AppBar>
            </Box>

            {isOpenEditDialog ? projectEditDialog(project, assignedUsers, usersToRemove, cancelEditProjectDialog, updateProject, setUsersToRemove, updateProjectDescription) : <div></div>}
        </div>
    )

}