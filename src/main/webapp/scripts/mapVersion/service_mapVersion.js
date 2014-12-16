'use strict';

mapgenApp.factory('MapVersion', function ($resource) {
        return $resource('app/rest/mapVersions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
