const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
    app.use(
        '/_api',
        createProxyMiddleware({
            target: 'http://pm-be:8080',
            changeOrigin: true,
            pathRewrite: {
                '^/_api' : '/api'
            }
        })
    );
};