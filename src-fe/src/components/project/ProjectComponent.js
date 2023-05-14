import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom'
import { Backdrop, Div, CircularProgress } from '@mui/material';
import deskController from '../../services/DeskController';
import projectComponentHeader from './ProjectComponentHeader';
import desksList from './ProjectcomponentDesksList';

export default function ProjectComponent() {
    const [searchParams, setSearchParams] = useSearchParams();
    const [projectId, setProjectId] = useState(searchParams.get('projectId'));
    const [project, setProject] = useState(null);
    const [isLoading, setLoading] = useState(true);

    useEffect(() => {
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
                {projectComponentHeader(projectId)}
            </div>
            <div style={{
                width: '100%',
                display: 'flex',
                justifyContent: 'center',
                height: '95%',
                marginTop: '5rem',
            }}>
                {desksList(projectId, setLoading)}
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