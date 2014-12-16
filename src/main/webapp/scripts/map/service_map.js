'use strict';

mapgenApp.factory('Map', function ($resource) {
        return $resource('app/rest/maps/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
