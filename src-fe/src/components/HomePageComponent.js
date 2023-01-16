import React, { useState, useEffect } from 'react';
import { Backdrop, CircularProgress } from '@mui/material';
import projects from './project/AllProjectsViewComponent';

export default function HomePageComponent() {
    
    return (
        <div
            style={{
                display: 'flex',
                height: '100vh',
                justifyContent: 'center'
            }}>
                {projects()}
        </div>)


}