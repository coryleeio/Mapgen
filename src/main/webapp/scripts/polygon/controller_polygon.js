'use strict';

mapgenApp.controller('PolygonController', function ($scope, resolvedPolygon, Polygon, resolvedPolygon, resolvedCorner, resolvedEdge, resolvedMap) {

        $scope.polygons = resolvedPolygon;
        $scope.polygons = resolvedPolygon;
        $scope.corners = resolvedCorner;
        $scope.edges = resolvedEdge;
        $scope.maps = resolvedMap;

        $scope.create = function () {
            Polygon.save($scope.polygon,
                function () {
                    $scope.polygons = Polygon.query();
                    $('#savePolygonModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.polygon = Polygon.get({id: id});
            $('#savePolygonModal').modal('show');
        };

        $scope.delete = function (id) {
            Polygon.delete({id: id},
                function () {
                    $scope.polygons = Polygon.query();
                });
        };

        $scope.clear = function () {
            $scope.polygon = {x: null, y: null, ocean: null, water: null, coast: null, elevation: null, id: null};
        };
    });
