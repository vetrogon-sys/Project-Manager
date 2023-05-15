import http from './http/HttpService';

export default function UeskController() {
    const baseUri = `/users`

    return {
        findAssignedToProjectWithId: function (projectId) {
            return http().GET(`${baseUri}/assignTo/projects/${projectId}`)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        findAllExclusions: function(existingIds) {
            return http().GET(`${baseUri}/exclusions/${existingIds.join()}`)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        isAssignedToTasktWithIdExist: function (taskId) {
            return http().HEAD(`${baseUri}/assignTo/tasks/${taskId}`)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        findAssignedToTasktWithId: function (taskId) {
            return http().GET(`${baseUri}/assignTo/tasks/${taskId}`)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        aboutMe: function() {
            return http().GET(`${baseUri}/aboutme`)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        }
    }
}