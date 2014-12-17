'use strict';

mapgenApp.factory('Polygon', function ($resource) {
        return $resource('app/rest/polygons/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
