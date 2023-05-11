import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom'
import { Card, CardContent, CardActions, Button, Typography, Backdrop, CircularProgress } from '@mui/material';
import deskController from '../../services/DeskController';
import projectComponentHeader from './ProjectComponentHeader';

async function getDesks(projectId, setIsLoading) {
    setIsLoading(true);

    const data = await deskController(projectId).findAll()
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });

    setIsLoading(false);

    return {
        desks: data,
    };

}

export default function ProjectComponent() {
    const [searchParams, setSearchParams] = useSearchParams();
    const [projectId, setProjectId] = useState(searchParams.get('projectId'));
    const [project, setProject] = useState(null);
    const [desks, setDesks] = useState(null);
    const [isLoading, setLoading] = useState(true);

    useEffect(() => {
        if (projectId === null) {
            setProjectId(searchParams.get('projectId'));
        }
        const fetchData = async () => {
            const response = await getDesks(projectId, setLoading);

            setDesks(response.desks);
        };

        fetchData();

    }, [])

    return (
        <div
            style={{
                display: 'flex',
                height: '100vh',
                justifyContent: 'center'
            }}>
                {projectComponentHeader(projectId)}
            <Backdrop
                sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
                open={isLoading}
            >
                <CircularProgress color="inherit" />
            </Backdrop>
        </div>

    )
}