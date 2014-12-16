'use strict';

mapgenApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/mapVersion', {
                    templateUrl: 'views/mapVersions.html',
                    controller: 'MapVersionController',
                    resolve:{
                        resolvedMapVersion: ['MapVersion', function (MapVersion) {
                            return MapVersion.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
