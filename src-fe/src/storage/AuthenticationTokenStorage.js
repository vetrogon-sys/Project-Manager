export default function AuthenticationTokenStorage() {
    const tokenKey = 'token'

    return {
        saveToken: function (value) {
            localStorage.setItem(tokenKey, value);
        },
        getToken: function () {
            return localStorage.getItem(tokenKey);
        },
        clearToken: function () {
            localStorage.removeItem(tokenKey);
        }
    }
}