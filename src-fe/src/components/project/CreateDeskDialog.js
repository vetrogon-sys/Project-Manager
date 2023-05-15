import React, { useState } from 'react';
import { Box, TextField, Button, } from '@mui/material';
import CheckIcon from '@mui/icons-material/Check.js';
import RemoveIcon from '@mui/icons-material/Remove.js';
import deskController from '../../services/DeskController';
import { deDE } from '@mui/x-date-pickers';

async function createDeskInProject(projectId, deskDto) {
    const data = await deskController(projectId).create(deskDto)
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });

    if (data.status === 400) {
        return {
            errors: data.data.errors
        }
    }
}

export default function CreateDesk(props) {
    const [name, setName] = useState(null);
    const [errorMessage, setErrorMessage] = useState(null);

    const handleNameInput = (event) => {
        setName(event.target.value);
    }

    const isNameValid = () => {
        const nameReg = new RegExp('(?=.{5,80}$)');
        if (name && nameReg.exec(name)) {
            return true;
        }
        return false;
    }

    const createDesk = async () => {
        if (!isNameValid()) {
            setErrorMessage('Desk name must be between 5 and 80 symbols');
            return;
        } else {
            setErrorMessage(null);
        }

        const deskDto = {
            name: name,
        };

        await createDeskInProject(props.projectId, deskDto);
        props.closeCreateMenu();
    }

    return (
        <div>
            <Box
                sx={{
                    display: 'contents',
                    justifyContent: 'center',
                    alignItems: 'center',
                    backgroundColor: 'rgb(199, 208, 235)',
                    maxHeight: '3rem',
                    fontWeight: 400,
                }}
            >
                <TextField
                    required
                    variant="filled"
                    error={errorMessage ? true : false}
                    helperText={errorMessage ? errorMessage : null}
                    label='Name'
                    FormHelperTextProps={{
                        style: {
                            backgroundColor: 'rgb(245, 247, 252)',
                            margin: 0,
                            padding: '.5rem',
                            fontSize: '10pt'
                        }
                    }}
                    onInput={handleNameInput}
                    size="small"
                />
            </Box>
            <Box sx={{
                alignSelf: 'flex-end',
            }}>
                <Button
                    onClick={createDesk}
                    sx={{
                        margin: '.5rem',
                    }}>
                    <CheckIcon />
                </Button>
                <Button
                    onClick={props.closeCreateMenu}
                    sx={{
                        margin: '.5rem',
                    }}>
                    <RemoveIcon />
                </Button>
            </Box>
        </div>
    );
}