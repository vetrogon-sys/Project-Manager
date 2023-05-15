import React, { useState, useEffect } from 'react';
import { Backdrop, CircularProgress, Box, Container, Card, CardContent, CardActions, Button, Typography } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import projectController from "../../services/ProjectController";
import projectElement from "./ProjectElement";
import CreateProject from './CreateProjectDialog';

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
        boxShadow: 4,
        textAlign: 'left',
        fontSize: '18pt',
        fontWeight: 400,
    };
}

export default function AllProjectsViewComponent() {
    const [ownProjects, setOwnProjects] = useState(null);
    const [assignedProjects, setAssignedProjects] = useState(null);
    const [isOpenCreateProjectDialog, setIsOpenCreateProjectDialog] = useState(false);
    const [isLoading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            const response = await getAllProjects(setLoading);

            setAssignedProjects(response.assignedProjects);
            setOwnProjects(response.ownProjects);
        };

        fetchData();

    }, [isOpenCreateProjectDialog]);

    // useEffect(() => {
    //     const fetchData = async () => {
    //         const response = await getAllProjects(setLoading);

    //         setAssignedProjects(response.assignedProjects);
    //         setOwnProjects(response.ownProjects);
    //     };

    //     fetchData();

    // }, [isOpenCreateProjectDialog]);

    const openCreateProjectDialog = () => {
        setIsOpenCreateProjectDialog(true);
    }

    const closeCreateProjectDialog = () => {
        setIsOpenCreateProjectDialog(false);
    }

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
                    <div style={{ margin: '5px 15px' }}>
                        Own Projects:
                    </div>
                    <Box sx={{
                        height: '85%',
                        overflow: 'auto',
                    }}>
                        {ownProjects ? ownProjects.map(
                            (project, i) => projectElement(project)
                        ) : null}
                    </Box>
                    <Box>
                        <Button
                            onClick={openCreateProjectDialog}
                            sx={{
                                margin: '.5rem'
                            }}>
                            <AddIcon />
                            Create new Project
                        </Button>
                    </Box>
                </Box>
                <Box sx={getProjectBoxStyles()}>
                    <div style={{ margin: '5px 15px' }}>
                        Assigned Projects:
                    </div>
                    <Box sx={{
                        height: '85%',
                        overflow: 'auto',
                    }}>
                        {assignedProjects ? assignedProjects.map(
                            (project, i) => projectElement(project)
                        ) : null}
                    </Box>
                </Box>
            </Container >

            {isOpenCreateProjectDialog
                ? <CreateProject
                    closeDialog={closeCreateProjectDialog} />
                : null}
            <Backdrop
                sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
                open={isLoading}
            >
                <CircularProgress color="inherit" />
            </Backdrop>
        </div>)


}