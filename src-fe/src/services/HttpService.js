import Axios from 'axios';
import authStorage from '../storage/AuthenticationTokenStorage';

const instance = Axios.create();

instance.interceptors.response.use(function (response) {
    return response;
}, function (error) {

    if (error.code.status === 403) {
        window.location.href = '/login'
    }

    return Promise.reject(error);
});

export default function HttpService() {
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${authStorage().getToken()}`
    }

    return {
        GET: function (url) {
            return instance.get(url, { headers }).then(response => {
                return response;
            }).catch(err => {
                throw err;
            });
        },
        POST: function (url, data) {
            return instance.post(url, data, { headers }).then(response => {
                return response;
            }).catch(err => {
                throw err;
            })
        }
    }
}