import Axios from 'axios';
import authStorage from '../../storage/AuthenticationTokenStorage';

const instance = Axios.create();

instance.interceptors.response.use(function (response) {
    return response;
}, function (error) {
    if (error.response.status === 403) {
        authStorage().clearToken();
        console.log(window.location.pathname)
        if (window.location.pathname !== '/login') {
            window.location.href = '/login'
        }

    }

    return Promise.reject(error);
});

export default function HttpService() {
    const baseApi = '/_api'

    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${authStorage().getToken()}`
    }

    return {
        GET: function (url) {
            return instance.get(`${baseApi}${url}`, { headers }).then(response => {
                return response;
            }).catch(err => {
                throw err;
            });
        },
        POST: function (url, data) {
            return instance.post(`${baseApi}${url}`, data, { headers }).then(response => {
                return response;
            }).catch(err => {
                throw err;
            })
        },
        PUT: function (url, data) {
            return instance.put(`${baseApi}${url}`, data, { headers }).then(response => {
                return response;
            }).catch(err => {
                throw err;
            })
        },
        PATCH: function (url, data) {
            return instance.patch(`${baseApi}${url}`, data, { headers }).then(response => {
                return response;
            }).catch(err => {
                throw err;
            })
        },
        HEAD: function (url) {
            return instance.head(`${baseApi}${url}`, { headers }).then(response => {
                return response;
            }).catch(err => {
                throw err;
            });
        },
        DELETE: function (url) {
            return instance.delete(`${baseApi}${url}`, { headers }).then(response => {
                return response;
            }).catch(err => {
                throw err;
            });
        }
    }
}