export default function AuthenticationTokenStorage() {
    const tokenKey = 'token'

    return {
        saveToken: function (value) {
            localStorage.setItem(tokenKey, value);
        },
        getToken: function () {
            localStorage.getItem(tokenKey);
        },
        cleareToken: function () {
            localStorage.removeItem(tokenKey);
        }
    }
}