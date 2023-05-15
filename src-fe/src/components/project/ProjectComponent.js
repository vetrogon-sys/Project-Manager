import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom'
import { Backdrop, Div, CircularProgress } from '@mui/material';
import deskController from '../../services/DeskController';
import ProjectComponentHeader from './ProjectComponentHeader';
import desksList from './ProjectcomponentDesksList';
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

export default function ProjectComponent() {
    const [searchParams, setSearchParams] = useSearchParams();
    const [projectId, setProjectId] = useState(searchParams.get('projectId'));
    const [assignedUsers, setAssignedUsers] = useState(null);
    const [project, setProject] = useState(null);
    const [isLoading, setLoading] = useState(true);

    useEffect(() => {
        if (projectId) {
            const fetchData = async () => {
                const assignedUsersResponse = await getAssignedUsers(projectId);
    
                setAssignedUsers(assignedUsersResponse.usersPage.content);
            };
    
            fetchData();
        }

        if (projectId === null) {
            setProjectId(searchParams.get('projectId'));
        }

    }, [])

    return (
        <div
            style={{
                display: 'flex',
                flexDirection: 'column',
                height: '90vh',
            }}>
            <div style={{
                width: '100%',
                position: 'fixed',
            }}>
                {<ProjectComponentHeader projectId={projectId} assignedUsers={assignedUsers} />}
            </div>
            <div style={{
                width: '100%',
                display: 'flex',
                justifyContent: 'center',
                height: '95%',
                marginTop: '5rem',
            }}>
                {desksList(projectId, assignedUsers, setLoading)}
            </div>
            <Backdrop
                sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
                open={isLoading}
            >
                <CircularProgress color="inherit" />
            </Backdrop>
        </div>

    )
}