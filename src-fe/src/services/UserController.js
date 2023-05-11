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
        }
    }
}