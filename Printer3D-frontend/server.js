// - D E P E N D E N C I E S
const express = require('express');
const path = require('path');
const compression = require('compression');
const config = require('config');
const proxy = require('express-http-proxy');
const fs = require('fs')
const exec = require('child_process').exec;

// - S E R V E R   O P T I O N S
const app = express();
app.use(compression());
var options = {
    dotfiles: 'ignore',
    etag: true,
    extensions: ['htm', 'html'],
    index: false,
    maxAge: '1d',
    lastModified: true,
    redirect: false,
    setHeaders: function(res, path, stat) {
        res.set('x-timestamp', Date.now())
        res.set('x-app-name', 'NeoCom.Infinity.Frontend')
        res.set('Access-Control-Allow-Origin', '*');
        res.set('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
        res.set('Access-Control-Allow-Methods', 'OPTIONS, GET, POST, PUT, DELETE');
    }
};
// - S E R V E R   C O N F I G U R A T I O N
app.locals.appname = config.get('settings.appname');
app.locals.version = require('./package.json').version;
app.locals.port = process.env.PORT || config.get('settings.port');
app.locals.applicationhome = config.get('settings.applicationhome');
app.locals.backendproxy = config.get('settings.backendproxy');
app.locals.apppath = config.get('settings.apppath');
app.locals.homepage = config.get('pages.homepage');
app.locals.staticpublic = config.get('pages.staticpublic');

// - L O G G I N G
app.use(function(req, res, next) {
    res.header('Access-Control-Allow-Origin', '*')
    res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
    res.header('Access-Control-Allow-Methods', 'OPTIONS, GET, POST, PUT, DELETE')
    if ('OPTIONS' === req.method) {
        res.sendStatus(200)
    } else {
        console.log(req.method + " " + req.url)
        next()
    }
})

// - H O M E   P A G E
// app.use(express.static(__dirname + '/public', options))
// app.get('/', function(req, res) {
//     //  console.log(req.method + ' ' + '/public' + '/home.html')
//     console.log("Home: " + __dirname + req.url)
//     res.status(200).sendFile(app.locals.homepage, { root: app.locals.staticpublic });
// });
// - S T A T I C
app.use(express.static(__dirname + app.locals.apppath, options));
app.get('*.*', function(req, res) {
    console.log("Static: " + __dirname + app.locals.applicationhome + req.url)
    res.status(200).sendFile(path.join(__dirname + app.locals.applicationhome + req.url));
});
// - A C T U A T O R   M O U N T P O I N T
app.use('/actuator', proxy(app.locals.backendproxy, {
    proxyReqPathResolver: function(req) {
        // console.log('Backend Host: ' + app.locals.backendproxy)
        console.log('Backend: ' + app.locals.backendproxy + 'actuator' + req.url)
        return app.locals.backendproxy + 'actuator' + req.url;
    }
}));
// - A P I   M O U N T P O I N T
app.use('/api', proxy(app.locals.backendproxy, {
    proxyReqPathResolver: function(req) {
        // console.log('Backend Host: ' + app.locals.backendproxy)
        console.log('Backend: ' + app.locals.backendproxy + 'api' + req.url)
        return app.locals.backendproxy + 'api' + req.url;
    }
}));
// - A P P   M O U N T P O I N T
app.get('/*', function(req, res) {
    console.log('App: ' + __dirname + app.locals.applicationhome + req.url)
    res.sendFile(path.join(__dirname + app.locals.applicationhome + '/index.html'));
});

const START_YELLOW = "\x1b[33m\x1b[1m"
const START_GREEN = "\x1b[32m\x1b[1m"
const END_BOLD = "\x1b[0m"
    // - L I S T E N
app.listen(process.env.PORT || app.locals.port || 3000, function() {
    exec('git describe --tags', (err, stdout, stderr) => {
        console.log("Node Express server version: v12.16.3");
        console.log("Running environment: " + START_YELLOW + process.env.NODE_ENV + END_BOLD);
        console.log("Current build: " + START_YELLOW + stdout.replace("\n", "") + END_BOLD)
        console.log("Listening on port: " + START_YELLOW + app.locals.port + END_BOLD);
        console.log("Serving application: " + START_YELLOW + app.locals.appname + END_BOLD);
        console.log("Serving application version: " + START_YELLOW + app.locals.version + END_BOLD);
        console.log("Application URL path: " + START_GREEN + "http://localhost:" + app.locals.port + '/' + END_BOLD);
        console.log("Backend URL path: " + START_GREEN + app.locals.backendproxy + END_BOLD);
        console.log("Proxy redirection for " + START_YELLOW + "/actuator" + END_BOLD + ": " +
            'Backend: ' + START_GREEN + app.locals.backendproxy + 'actuator' + END_BOLD)
        console.log("Proxy redirection for " + START_YELLOW + "/api" + END_BOLD + ": " +
            'Backend: ' + START_GREEN + app.locals.backendproxy + 'api' + END_BOLD);
        console.log("Proxy redirection for " + START_YELLOW + "/" + END_BOLD + ": " +
            'Local: ' + START_GREEN + app.locals.applicationhome + END_BOLD);
        const filename = 'app-banner.txt'
        fs.readFile(filename, 'utf8', function(err, data) {
            if (err) throw err;
            console.log(data)
            console.log("Server Ready for Connections");
        });
    })
});

function execute(command, callback) {
    exec(command, function(error, stdout, stderr) { callback(stdout); });
};