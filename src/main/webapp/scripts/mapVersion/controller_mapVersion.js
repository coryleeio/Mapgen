'use strict';

mapgenApp.controller('MapVersionController', function ($scope, resolvedMapVersion, MapVersion) {

        $scope.mapVersions = resolvedMapVersion;

        $scope.create = function () {
            MapVersion.save($scope.mapVersion,
                function () {
                    $scope.mapVersions = MapVersion.query();
                    $('#saveMapVersionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.mapVersion = MapVersion.get({id: id});
            $('#saveMapVersionModal').modal('show');
        };

        $scope.delete = function (id) {
            MapVersion.delete({id: id},
                function () {
                    $scope.mapVersions = MapVersion.query();
                });
        };

        $scope.clear = function () {
            $scope.mapVersion = {maxY: null, maxX: null, version: null, id: null};
        };
    });
