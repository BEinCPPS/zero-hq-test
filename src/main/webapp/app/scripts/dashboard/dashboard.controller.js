(function () {
    'use strict';

    angular
        .module('zerohqt.dashboard')
        .controller('DashboardController', DashboardController);

    DashboardController.$inject = ['$scope', 'dashboardOrionService'];

    /* @ngInject */
    function DashboardController($scope, dashboardOrionService) {

        $scope.data = [];
        $scope.$on('$ionicView.loaded', function (viewInfo, state) {
            dashboardOrionService.getFeedbackScale().then(function (req) {
                if (req.data) {
                    $scope.data = req.data;
                }
            }, function (error) {
                console.log('Error creating subscriptions: ' + error);
            });
        });

        $scope.controlValue = function (value, valueMin, valueMax) {
            if (value < valueMax && value > valueMin)  return 'green-button';
            else if (value < valueMin) return 'yellow-button';
            else if (value > valueMax) return 'red-button';

        }

        $scope.$on('wsMessageFeedback', function (event, feedbackInfo) {
            for (var i in $scope.data) {
                var feedback = $scope.data[i];
                if (feedback.measureId === feedbackInfo.measureId) {
                    feedback.value = feedbackInfo.value;
                }
            }
            $scope.$apply(); //Apply changes to the page
        });

    }
})();
