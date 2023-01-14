import React, { useState, useEffect } from 'react';
import { Outlet } from "react-router-dom";
import auth from '../services/AuthorizationService';
import authStorage from '../storage/AuthenticationTokenStorage';
import { Box, FormControl, Typography, Button, Backdrop, CircularProgress, IconButton } from '@mui/material';
import ArrowBack from "@mui/icons-material/ArrowBack";
import input from "./input/TextFieldInputComponent";

function sendtoapi(registrationData) {
    return auth().signUp(registrationData)
        .then((response) => {
            return response;
        })
        .catch((err) => {
            return err.response;
        });
}

async function authorize(registrationData, setIsLoading, setErrors) {
    setIsLoading(true);
    const response = await sendtoapi(registrationData);

    setIsLoading(false);
    if (response.status === 200) {
        authStorage().saveToken(response.data.token);
        window.location.href='/'
    } else {
        console.log(response)
        setErrors(new Map(Object.entries(response.data.errors)));
    }
}

export default function RegistrationComponent() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [passwordRepeat, setPasswordRepeat] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [isLoading, setLoading] = useState(false);
    const [errors, setErrors] = useState(new Map());

    const callAuth = () => {
        if (password === passwordRepeat) {
            const registrationData = {
                email: email,
                password: password,
                lastName: lastName,
                firstName: firstName
            }
    
            authorize(registrationData, setLoading, setErrors);
            
        } else {
            errors.set('password', 'Passwords does`t match');
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
                    height: '35rem',
                    boxShadow: 4,
                    padding: '.5rem'
                }}>
                <FormControl
                    sx={{
                        display: 'flex',
                        flexDirection: 'row',
                        justifyContent: 'space-between'
                    }}
                    variant="outlined">
                    <IconButton href='/login' >
                        <ArrowBack />
                    </IconButton>
                    <Typography
                        sx={{
                            marginTop: '5px',
                            marginRight: '24%'
                        }}
                        mt={2}
                        variant="h6">
                        Hello in ProjectManager
                    </Typography>
                </FormControl>
                {console.log(errors)}
                {input().defaultTextInput('Email', setEmail, errors.get('email'))}
                {input().securedTextInput('Password', setPassword, errors.get('error'))}
                {input().securedTextInput('Repeat password', setPasswordRepeat, errors.get('password'))}
                {input().defaultTextInput('First Name', setFirstName, errors.get('email'))}
                {input().defaultTextInput('Last Name', setLastName, errors.get('email'))}
                <FormControl sx={{ m: 1, width: '40ch' }} variant="outlined">
                    <Button variant="contained" onClick={callAuth}>
                        Sign on
                    </Button>
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