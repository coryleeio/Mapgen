'use strict';

mapgenApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/map', {
                    templateUrl: 'views/maps.html',
                    controller: 'MapController',
                    resolve:{
                        resolvedMap: ['Map', function (Map) {
                            return Map.query().$promise;
                        }],
                        resolvedMapVersion: ['MapVersion', function (MapVersion) {
                            return MapVersion.query().$promise;
                        }],
                        resolvedUser: ['User', function (User) {
                            return User.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
