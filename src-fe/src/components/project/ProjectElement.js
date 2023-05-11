import React from 'react';
import { Card, CardContent, CardActions, Button, Typography } from '@mui/material';

export default function ProjectElement(project) {

    const goToProject = () => {
        window.location.href = `/projects?projectId=${project.id}`;
    }

    return (
        <Card sx={{ minWidth: 275, margin: 2, textAlign: 'left' }}>
            <CardContent>
                <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                    Project {project.id}
                </Typography>
                <Typography variant="h5" component="div">
                    {project.name}
                </Typography>
                <Typography sx={{ mb: 1.5 }} color="text.secondary">
                    adjective
                </Typography>
                <Typography variant="body2">
                    {project.description ? project.description : 'There isn\'t description here...'}
                </Typography>
            </CardContent>
            <CardActions>
                <Button size="small" onClick={goToProject}>See project</Button>
            </CardActions>
        </Card>
    )
}