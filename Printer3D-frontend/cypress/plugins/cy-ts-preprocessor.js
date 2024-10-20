const wp = require('@cypress/webpack-preprocessor')

const webpackOptions = {
    resolve: {
        extensions: ['.ts', '.js']
    },
    node: { fs: "empty", child_process: "empty", readline: "empty" },
    module: {
        rules: [{
                test: /\.ts$/,
                exclude: [/node_modules/],
                use: [{
                    loader: 'ts-loader'
                }]
            },
            {
                test: /\.feature$/,
                use: [{
                    loader: "cypress-cucumber-preprocessor/loader"
                }]
            }
        ]
    }
}

const options = {
    webpackOptions
}

module.exports = wp(options)
