const scanner = require('sonarqube-scanner')
const package = require('./package.json')

scanner({
        serverUrl: 'https://sonarcloud.io',
        token: "760417486be1e97e4566898079652a8ac326af3d",
        options: {
            'sonar.host.url': 'https://sonarcloud.io',
            'sonar.organization': 'dimensinfin3d',
            'sonar.projectKey': 'dimensinfin3d_printer3d_frontend',
            'sonar.projectName': 'Printer3D - frontend',
            'sonar.projectVersion': package.version,
            'sonar.projectDescription': 'Front end Angular application to manage the production jobs on a set of 3D printers.',
            'sonar.sources': './src',
            'sonar.sourceEncoding': 'UTF-8',
            'sonar.exclusions': '**/node_modules/**',
            'sonar.tests': 'src',
            'sonar.test.inclusions': '**/*.spec.ts',
            'sonar.typescript.lcov.reportPaths': 'coverage/Printer3D-frontend/lcov.info'
        }
    },
    () => process.exit()
)