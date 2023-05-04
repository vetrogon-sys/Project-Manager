import React, { useState, useEffect } from 'react';
import { Backdrop, CircularProgress, Box, Container } from '@mui/material';
import projectController from "../../services/ProjectController";

function findOwnProjects() {

    return projectController().findOwnProjects()
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });
}

function findAssignedProjects() {

    return projectController().findAssignedProjects()
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });
}

async function getAllProjects(setIsLoading) {
    setIsLoading(true);

    const ownProjects = await findOwnProjects();

    const assignedProjects = await findAssignedProjects();

    setIsLoading(false);

    return {
        ownProjects: ownProjects,
        assignedProjects: assignedProjects
    };

}

function getProjectBoxStyles() {
    return {
        width: '48%',
        height: '96%',
        boxShadow: 4
    };
}

export default function AllProjectsViewComponent() {
    const [email, setEmail] = useState('');
    const [ownProjects, setOwnProjects] = useState(null);
    const [assignedProjects, setAssignedProjects] = useState(null);
    const [isLoading, setLoading] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            const response = await getAllProjects();

            setOwnProjects(response.ownProjects);
            setAssignedProjects(response.assignedProjects);
        };

        fetchData();
    })

    return (
        <div style={{
            marginTop: '5rem',
            width: '100%',
        }}>
            <Container sx={{
                width: '80%',
                height: '100%',
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'space-between'
            }}>
                <Box sx={getProjectBoxStyles()}>
                    OWN:
                </Box>
                <Box sx={getProjectBoxStyles()}>
                    ASSIGNED:
                </Box>
            </Container >

            <Backdrop
                sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
                open={isLoading}
            >
                <CircularProgress color="inherit" />
            </Backdrop>
        </div>)


}