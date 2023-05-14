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
        },
        create: function (deskDto) {
            return http().POST(`${baseUri}`, deskDto)
                .then(response => {
                    return response;
                }).catch(err => {
                    throw err;
                });
        }
    }
}