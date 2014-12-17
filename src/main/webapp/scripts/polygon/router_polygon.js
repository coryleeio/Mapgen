'use strict';

mapgenApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/polygon', {
                    templateUrl: 'views/polygons.html',
                    controller: 'PolygonController',
                    resolve:{
                        resolvedPolygon: ['Polygon', function (Polygon) {
                            return Polygon.query().$promise;
                        }],
                        resolvedPolygon: ['Polygon', function (Polygon) {
                            return Polygon.query().$promise;
                        }],
                        resolvedCorner: ['Corner', function (Corner) {
                            return Corner.query().$promise;
                        }],
                        resolvedEdge: ['Edge', function (Edge) {
                            return Edge.query().$promise;
                        }],
                        resolvedMap: ['Map', function (Map) {
                            return Map.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
