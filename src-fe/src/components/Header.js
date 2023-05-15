import React, { useState, useEffect } from 'react';
import { Box, Avatar, FormControl, Typography, Menu, MenuItem, IconButton } from '@mui/material';
import userController from '../services/UserController';
import tokenStorage from '../storage/AuthenticationTokenStorage';

async function getAboutMeInformation() {
    const data = await userController().aboutMe()
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });

    return {
        user: data,
    };
}

export default function Header() {

    const [user, setUser] = useState(null);
    const [isUserMenuOpen, setIsUserMenuOpen] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            const response = await getAboutMeInformation();

            setUser(response.user);
        };

        fetchData();
    }, [])

    const handleOpenUserMenu = () => {
        setIsUserMenuOpen(!isUserMenuOpen);
    };

    const handleCloseUserMenu = () => {
        setIsUserMenuOpen(false);
    }

    const logout = () => {
        tokenStorage().clearToken();
        window.location.href = '/login';
    }

    const getAvatarForCurrentUser = () => {
        return (
            <IconButton onClick={handleOpenUserMenu}>
                <Avatar
                    alt={`${user.firstName} ${user.lastName}`} />
            </IconButton>
        );
    }

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
                zIndex: '100000000'
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
                    ? getAvatarForCurrentUser()
                    : null}
                <Menu
                    sx={{ mt: '45px' }}
                    anchorEl={isUserMenuOpen}
                    anchorOrigin={{
                        vertical: 'top',
                        horizontal: 'right',
                    }}
                    keepMounted
                    transformOrigin={{
                        vertical: 'top',
                        horizontal: 'right',
                    }}
                    open={isUserMenuOpen}
                    onClose={handleCloseUserMenu}
                >
                    <MenuItem key={'cred'} onClick={handleCloseUserMenu}>
                        <Typography textAlign="center">{user ? (user.firstName + ' ' + user.lastName) : null}</Typography>
                    </MenuItem>
                    <MenuItem key={'logout'} onClick={logout}>
                        <Typography textAlign="center">Logout</Typography>
                    </MenuItem>
                </Menu>
            </FormControl>

        </Box>)


}