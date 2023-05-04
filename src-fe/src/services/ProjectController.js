import http from './http/HttpService';

export default function ProjectController() {
    const baseUri = '/projects'

    return {
        findOwnProjects: function () {
            return http().GET(`${baseUri}/myself/created`)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        findAssignedProjects: function () {
            return http().GET(`${baseUri}/myself/assigned`)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
    }
}