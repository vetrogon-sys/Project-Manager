import http from './http/HttpService';

export default function TaskController(deskId) {
    const baseUri = `/desks/${deskId}/tasks`

    return {
        findAllInDesk: function () {
            return http().GET(`${baseUri}`)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        moveTaskToAnotherDesk: function (taskId, updatedDeskId) {
            return http().PATCH(`${baseUri}/${taskId}/moveTo/desks/${updatedDeskId}`, null)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        createTaskInDesk: function (taskDto) {
            return http().POST(`${baseUri}`, taskDto)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        editTaskInDesk: function (taskId, taskDto) {
            return http().PATCH(`${baseUri}/${taskId}`, taskDto)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        assignUserToTask: function (taskId, userId) {
            return http().PATCH(`${baseUri}/${taskId}/users/${userId}`, null)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        unassignUserFromTask: function (taskId) {
            return http().DELETE(`${baseUri}/${taskId}/users`)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
        deleteById: function (taskId) {
            return http().DELETE(`${baseUri}/${taskId}`)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        },
    }
}