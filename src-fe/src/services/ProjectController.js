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
        findById: function (id) {
            return http().GET(`${baseUri}/${id}`)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        unassignUsers: function (projectId, usersIds) {
            return http().DELETE(`${baseUri}/${projectId}/users/${usersIds.join()}`)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        assignUsers: function (projectId, usersIds) {
            return http().PATCH(`${baseUri}/${projectId}/users/${usersIds.join()}`, null)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        updateProject: function(projectId, project) {
            const projectDto = {
                id: projectId,
                descrition: project.descrition
            }
            return http().PATCH(`${baseUri}/${projectId}`, projectDto)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        }
    }
}