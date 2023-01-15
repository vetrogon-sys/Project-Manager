import React, { useState, useEffect } from 'react';
import { Outlet } from "react-router-dom";
import auth from '../services/AuthorizationService';
import authStorage from '../storage/AuthenticationTokenStorage';
import { Box, FormControl, Typography, Button, Link, Backdrop, CircularProgress } from '@mui/material';
import input from "./input/TextFieldInputComponent";

function getCredentialsErrors(registrationData) {
    const emailRegExp = new RegExp('[a-z0-9]+@[a-z]+\\.[a-z]{2,3}');

    var errorMap = new Map();
    if (!emailRegExp.test(registrationData.email)) {
        errorMap.set('email', 'Invalid email');
    }

    const userInfoRegExp = new RegExp('^[a-zA-Z]{2,60}$');
    if (!userInfoRegExp.test(registrationData.firstName)) {
        errorMap.set('firstName', 'First name must not be blank');
    }
    if (!userInfoRegExp.test(registrationData.lastName)) {
        errorMap.set('lastName', 'Last name must not be blank');
    }

    const passwordRegExp = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    if (!passwordRegExp.test(registrationData.password)) {
        errorMap.set('password', 'Password must be minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character');
    }

    return errorMap;
}

function sendtoapi(email, pass) {
    const credentials = {
        email: email,
        password: pass
    };

    return auth().signIn(credentials)
        .then((response) => {
            return response;
        })
        .catch((err) => {
            return err.response;
        });
}

async function authenticate(email, password, setIsLoading, setErrors) {
    setIsLoading(true);
    const response = await sendtoapi(email, password);

    setIsLoading(false);
    if (response.status === 200) {
        authStorage().saveToken(response.data.token);
        window.location.href = '/'
    } else {
        setErrors(new Map(Object.entries(response.data.errors)));
    }

}

export default function LoginComponent() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errors, setErrors] = useState(new Map());
    const [isLoading, setLoading] = useState(false);

    const callAuth = () => {
        const emailRegExp = new RegExp('[a-z0-9]+@[a-z]+\\.[a-z]{2,3}');

        if (emailRegExp.test(email)) {
            authenticate(email, password, setLoading, setErrors);
        } else {
            setErrors(new Map(errors.set('email', 'Invalid email')));
        }

    }

    return (
        <div
            style={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                height: '100vh',
            }}>
            <Box
                sx={{
                    width: '25rem',
                    height: '22rem' + errors.size * 0.75,
                    boxShadow: 4
                }}>
                <FormControl sx={{ m: 1, width: '40ch' }} variant="outlined">
                    <Typography mt={2} variant="h6">
                        Sign in to ProjectManager
                    </Typography>
                </FormControl>
                {input().defaultTextInput('Email', setEmail, errors.get('email'))}
                {input().securedTextInput('Password', setPassword, errors.get('error'))}
                <FormControl sx={{ m: 1, width: '40ch' }} variant="outlined">
                    <Button variant="contained" disableElevation onClick={callAuth}>
                        Sign In
                    </Button>
                </FormControl>
                <FormControl sx={{ m: 1, width: '40ch' }}>
                    <Typography sx={{ fontSize: '16px' }} mt={2} variant="h6">
                        New to ProjectManager? <Link href='/registration'>Create an account.</Link>
                    </Typography>
                </FormControl>
                <Backdrop
                    sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
                    open={isLoading}
                >
                    <CircularProgress color="inherit" />
                </Backdrop>
            </Box>
            <Outlet />
        </div>)


}