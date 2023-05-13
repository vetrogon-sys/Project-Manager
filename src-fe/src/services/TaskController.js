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
        }
    }
}