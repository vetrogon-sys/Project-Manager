import React, { useState, useEffect } from 'react';
import { Box, AppBar, Toolbar, Typography, AvatarGroup } from '@mui/material';
import projectController from '../../services/ProjectController';

async function getProjectById(projectId) {
    const data = await projectController().findById(projectId)
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });

    console.log(data)
    return {
        project: data,
    };

}

export default function ProjectComponentHeader(projectId) {
    const [project, setProject] = useState(null);
    const [assignedUsers, setAssignedUsers] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            const response = await getProjectById(projectId);

            setProject(response.project);
        };

        fetchData();

    }, [])

    const getAssignedUsersAvatars = (assignedUsers) => {

        return (
            <AvatarGroup>

            </AvatarGroup>
        )
    }

    return (
        <div style={{
            height: 15,
            marginTop: '4rem',
            width: '100%',
            opacity: '.7'
        }}>
            <Box sx={{ flexGrow: 1 }}>
                <AppBar position="static">
                    <Toolbar variant="dense">
                        <Typography variant="h6" color="inherit" component="div" sx={{ flexGrow: 1 }}>
                            {project ? project.name : ''}
                        </Typography>
                        {assignedUsers ? getAssignedUsersAvatars(assignedUsers) : <div></div>}
                    </Toolbar>
                </AppBar>
            </Box>
        </div>
    )

}