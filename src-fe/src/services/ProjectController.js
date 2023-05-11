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
            const userIdsList = {
                userIds: usersIds
            }
            return http().PUT(`${baseUri}/${projectId}/unassign/users`, userIdsList)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        assignUsers: function (projectId, usersIds) {
            const userIdsList = {
                userIds: usersIds
            }
            return http().PUT(`${baseUri}/${projectId}/assign/users/`, userIdsList)
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
            return http().PUT(`${baseUri}/${projectId}`, projectDto)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        }
    }
}