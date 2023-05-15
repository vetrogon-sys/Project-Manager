import React, { useState, useEffect } from 'react';
import { FormControl, TextField, OutlinedInput, InputAdornment, IconButton, InputLabel, FormHelperText } from '@mui/material';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';

export default function TextFieldInputComponent() {
    const [isShowSecuredLabel, setShowSecuredLabel] = useState(false);

    return {
        defaultTextInput: function (labelValue, _setLabelCallback, helperText) {
            return (
                <FormControl sx={{ m: 1, width: '40ch' }} variant="outlined">
                    <TextField
                        label={labelValue}
                        variant="outlined"
                        error={helperText ? true : false}
                        helperText={helperText ? helperText : ''}
                        onChange={e => _setLabelCallback(e.target.value)} />
                </FormControl>)
        },
        securedTextInput: function (labelValue, _setLabelCallback, helperText) {

            const handleClickShowSecuredLabel = () => {
                setShowSecuredLabel((show) => !show);
            }

            const handleMouseDownSecuredLabel = (event) => {
                event.preventDefault();
            };

            return (<FormControl sx={{ m: 1, width: '40ch' }} variant="outlined">
                <InputLabel htmlFor="outlined-adornment-password">{labelValue}</InputLabel>
                <OutlinedInput
                    id="outlined-adornment-password"
                    type={isShowSecuredLabel ? 'text' : 'password'}
                    error={helperText ? true : false}
                    endAdornment={
                        <InputAdornment position="end">
                            <IconButton
                                aria-label="toggle password visibility"
                                onClick={handleClickShowSecuredLabel}
                                onMouseDown={handleMouseDownSecuredLabel}
                                edge="end"
                            >
                                {isShowSecuredLabel ? <VisibilityOff /> : <Visibility />}
                            </IconButton>
                        </InputAdornment>
                    }
                    label={labelValue}
                    onChange={e => _setLabelCallback(e.target.value)}
                />
                <FormHelperText
                    error={helperText ? true : false}
                >{helperText}</FormHelperText>
            </FormControl>)
        }
    }



}