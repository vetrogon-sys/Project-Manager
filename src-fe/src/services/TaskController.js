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
        }
    }
}