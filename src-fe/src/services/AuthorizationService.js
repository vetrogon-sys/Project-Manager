import Axios from 'axios';

export default function HttpServAuthorizationService() {
    const baseUri = '/_api/auth'

    const headers = {
        'Content-Type': 'application/json'
    }

    return {
        signIn: function (credentials) {
            return Axios.post(`${baseUri}/signin`, credentials, { headers })
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        signUp: function (registrationData) {
            return Axios.post(`${baseUri}/signup`, registrationData, { headers })
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
    }
}