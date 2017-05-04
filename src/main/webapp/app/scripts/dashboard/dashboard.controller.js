(function () {
    'use strict';

    angular
        .module('zerohqt.dashboard')
        .controller('DashboardController', DashboardController);

    DashboardController.$inject = ['$scope'];

    /* @ngInject */
    function DashboardController($scope) {
    $scope.ctrl = this;

  //  ctrl.add = add;
    $scope.ctrl.data = [
        {
            name: "MeasureA",
            code: 1500,
            limitMax: 2500,
            limitMin:  500
        },
        {
            name: "MeasureB",
            code: 3100,
            limitMax: 3000,
            limitMin: 2500
        },
        {
            name: "MeasureC",
            code: 200,
            limitMax: 2500,
            limitMin:  500
         },
         {
            name: "MeasureD",
            code: 4500,
            limitMax: 2500,
            limitMin:  500
          }
    ]
    $scope.controlValue = function (value, valueMin, valueMax) {
            if (value < valueMax && value > valueMin)  return 'green-button';
            else if (value < valueMin) return 'yellow-button';
            else if (value > valueMax) return 'red-button';

        }

    }
})();
