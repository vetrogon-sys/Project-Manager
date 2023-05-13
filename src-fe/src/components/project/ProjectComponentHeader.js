import React, { useState, useEffect } from 'react';
import { Box, AppBar, Toolbar, Typography, AvatarGroup, Avatar } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import AddIcon from '@mui/icons-material/Add';
import BackIcon from '@mui/icons-material/ArrowBack';
import projectEditDialog from './ProjectEditDialog';
import assignUserDialog from './AssignUsersToProjectDialog';
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

async function getUnassignedUsers(alreadyAssignedUsers) {
    if (!alreadyAssignedUsers) {
        return;
    }
    const data = await userController().findAllExclusions(alreadyAssignedUsers.map(user => user.id))
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });

    return {
        users: data.content,
        totalPageCount: data.totalElements,
        pageNumber: data.number
    };

}

export default function ProjectComponentHeader(projectId) {
    const [project, setProject] = useState(null);
    const [assignedUsers, setAssignedUsers] = useState(null);
    const [assignedUsersTotalyCount, setAssignedUsersTotalyCount] = useState(0);
    const [usersToRemove, setUsersToRemove] = useState(new Array())
    const [usersToAssign, setUsersToAssign] = useState(new Array())
    const [unassignedUsers, setUnassignedUsers] = useState(new Array())
    const [isOpenEditDialog, setIsOpenEditDialog] = useState(false);
    const [isOpenAssignUserDialog, setIsOpenAssignUserDialog] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            const projectResponse = await getProjectById(projectId);

            setProject(projectResponse.project);

            const assignedUsersResponse = await getAssignedUsers(projectId);

            setAssignedUsers(assignedUsersResponse.usersPage.content);
            setAssignedUsersTotalyCount(assignedUsersResponse.usersPage.totalElements)
        };

        fetchData();

    }, [isOpenEditDialog, isOpenAssignUserDialog])

    useEffect(() => {
        const fetchData = async () => {
            const users = await getUnassignedUsers(assignedUsers);

            setUnassignedUsers(users);
        };

        fetchData();

    }, [isOpenAssignUserDialog])

    const goToAllProjects = () => {
        window.location.href = '/';
    }

    const getAvatarForUser = (user) => {
        return (
            <Avatar key={user.id} alt={user.firstName + ' ' + user.lastName} src={user.imgUrl} >
                {user.firstName.charAt(0) + ' ' + user.lastName.charAt(0)}
            </Avatar>
        )
    }

    const updateProject = (project) => {
        setProject(project);
        cancelEditProjectDialog();
    }

    const updateProjectDescription = (updDescrtiption) => {
        setProject({ ...project, description: updDescrtiption })
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

    const openAssignUserDialog = () => {
        setIsOpenAssignUserDialog(true);
    }

    const cancelAssignUserDialog = () => {
        setIsOpenAssignUserDialog(false);
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
                    <Toolbar
                        variant="dense"
                        sx={{
                            display: 'flex',
                            justifyContent: 'space-between'
                        }}>
                        <Box sx={{
                            width: 'fit-content',
                            display: 'flex'
                        }}>
                            <IconButton onClick={goToAllProjects} color='default'>
                                <BackIcon />
                            </IconButton>
                            <Typography
                                variant="h5"
                                color="inherit"
                                component="div"
                                sx={{ 
                                    paddingTop: '.2rem' ,
                                    paddingLeft: '1rem'
                                    }} >
                                {project ? project.name : ''}
                            </Typography>
                            <IconButton onClick={openEditProjectDialog} color='default'>
                                <AddIcon />
                            </IconButton>
                        </Box>
                        <Box sx={{
                            width: 'fit-content',
                            display: 'flex'
                        }}>
                            <AvatarGroup max={5}>
                                {assignedUsersTotalyCount ? getAssignedUsersAvatars() : <div></div>}
                            </AvatarGroup>
                            <IconButton onClick={openAssignUserDialog} >
                                <AddIcon />
                            </IconButton>
                        </Box>
                    </Toolbar>
                </AppBar>
            </Box>

            {isOpenEditDialog ? projectEditDialog(project, assignedUsers, usersToRemove, cancelEditProjectDialog, updateProject, setUsersToRemove, updateProjectDescription) : <div></div>}
            {isOpenAssignUserDialog ? assignUserDialog(project, usersToAssign, unassignedUsers, cancelAssignUserDialog, setUsersToAssign) : <div></div>}
        </div>
    )

}