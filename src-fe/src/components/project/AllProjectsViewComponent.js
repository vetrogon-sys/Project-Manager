import React, { useState, useEffect } from 'react';
import { Backdrop, CircularProgress, Box, Container, Card, CardContent, Typography } from '@mui/material';
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

function ProjectElement(project) {
    const [id, setId] = useState(project.id);
    const [name, setName] = useState(project.name);
    const [description, setDescription] = useState(project.description);

    return (
        <Card>
            <CardContent>
                <Typography variant="h5">
                    {name}
                </Typography>
                <Typography variant="body2">
                    {description}
                </Typography>
            </CardContent>
        </Card>
    )
}

export default function AllProjectsViewComponent() {
    const [ownProjects, setOwnProjects] = useState(null);
    const [assignedProjects, setAssignedProjects] = useState(null);
    const [isLoading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            const response = await getAllProjects(setLoading);

            setAssignedProjects(response.assignedProjects);
            setOwnProjects(response.ownProjects);
        };

        fetchData();

    }, [])

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
                    <>
                        {ownProjects ? ownProjects.map(
                            (project, i) => <Card>
                                <CardContent>
                                    <Typography variant="h5">
                                        {project.name}
                                    </Typography>
                                    <Typography variant="body2">
                                        {project.description}
                                    </Typography>
                                </CardContent>
                            </Card>
                        ) : null}
                    </>
                </Box>
                <Box sx={getProjectBoxStyles()}>
                    ASSIGNED:
                    <>
                        {assignedProjects ? assignedProjects.map(
                            (project, i) => <Card>
                                <CardContent>
                                    <Typography variant="h5">
                                        {project.name}
                                    </Typography>
                                    <Typography variant="body2">
                                        {project.description}
                                    </Typography>
                                </CardContent>
                            </Card>
                        ) : null}
                    </>
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