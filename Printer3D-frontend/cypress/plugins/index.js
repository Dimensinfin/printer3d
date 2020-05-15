const cypressTypeScriptPreprocessor = require('./cy-ts-preprocessor')

module.exports = (on, config) => {
    // - CODE COVERAGE
    require('@cypress/code-coverage/task')(on, config)
        // - TYPESCRIPT PREPROCESSING
    on("file:preprocessor", cypressTypeScriptPreprocessor);
    // - ENVRONMENT CONFIGURATION
    const targetEnv = config.env.TARGET_ENV || 'dev';
    const environmentConfig = require(`./config/${targetEnv}`);
    return {
        ...config,
        ...environmentConfig,
    };
}
