import React, { useState, useEffect } from 'react';
import http from '../services/http/HttpService';
import { Box, Avatar, FormControl, Typography } from '@mui/material';

function getAboutMeInformation() {

    return http().GET('/users/aboutme')
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });
}

async function findMe(setIsLoading) {
    setIsLoading(true);

    const aboutMeResponce = await getAboutMeInformation();

    setIsLoading(false);

    return aboutMeResponce;

}

function getAvatarForUser(user) {

    return (
        <Avatar
            alt={`${user.firstName} ${user.lastname}`} />
    );
}

function getDefaultAvatar() {
    return (
        <Avatar />
    );
}

export default function HomePageComponent() {

    const [user, setUser] = useState(null);

    useEffect(() => {
        const fetchData = () => {
            const response = findMe();

            setUser(response);
        };

        fetchData();
    })

    return (
        <Box
            sx={{
                position: 'fixed',
                display: 'flex',
                alignSelf: 'flex-start',
                width: '100%',
                height: '4rem',
                backgroundColor: 'primary.dark',
                alignItems: 'center',
                justifyContent: 'space-between',
            }}
        >
            <div></div>
            <FormControl>
                <Typography sx={{
                    color: 'azure',
                    marginLeft: '1.25rem',
                    fontWeight: 'bold',
                    fontSize: '1.8em'
                }}>
                    ProjectManager
                </Typography>
            </FormControl>
            <FormControl
                sx={{
                    m: 1,
                    marginRight: '1.25rem'
                }}
                variant="outlined">
                {user !== null
                    ? getAvatarForUser(user)
                    : getDefaultAvatar()}
            </FormControl>

        </Box>)


}