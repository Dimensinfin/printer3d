// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
// declare var System: any;
// declare var name: string;
// declare var version: string;

// System.import('/assets/js/regular-expresions.js').then(file => {
//     name = file.name;
//     version = file.varsion;
// });
export const environment = {
    production: false,
    mockStatus: true,
    showexceptions: true,
    backendPath: 'http://localhost:5100',
    copyright: '© 2019,2020 Dimensinfin Industries',
    appName: require('../../package.json').name,
    appVersion: require('../../package.json').version + ' dev',
    appSignature: "S000.01.001-20200518",
    platform: 'Angular 9.1.7 - RxJs 6.5.4 - Rollbar 2.16.1',
    apiVersion1: '/api/v1'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
