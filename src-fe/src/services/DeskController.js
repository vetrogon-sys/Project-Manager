import http from './http/HttpService';

export default function DeskController(projectId) {
    const baseUri = `/projects/${projectId}/desks`

    return {
        findAll: function () {
            return http().GET(`${baseUri}`)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        }
    }
}