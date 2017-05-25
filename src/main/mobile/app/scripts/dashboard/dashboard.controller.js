(function () {
    'use strict';

    angular
        .module('zerohqt.dashboard')
        .controller('DashboardController', DashboardController);

    DashboardController.$inject = ['$scope', 'dashboardOrionService', 'websocketService', '$rootScope'];

    /* @ngInject */
    function DashboardController($scope, dashboardOrionService, websocketService, $rootScope) {
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

        $scope.getIconClass = function (value, valueMin, valueMax) {
            if (value < valueMax && value > valueMin)  return 'icon ion-ios-checkmark green';
            else if (value < valueMin) return 'icon ion-arrow-down-b orange';
            else if (value > valueMax) return 'icon ion-arrow-up-b red';
        }

        function aggregateData(feedbackInfo) {
            for (var i in $scope.data) {
                var feedback = $scope.data[i];
                if (feedback.measureId === feedbackInfo.measureId) {
                    feedback.value = feedbackInfo.value;
                }
            }
        }

        $scope.$on('wsMessageFeedback', function (event, feedbackInfo) {
            aggregateData(feedbackInfo);
            $scope.$apply(); //Apply changes to the page
        });

        $scope.$on('$ionicView.enter', function (viewInfo, state) {
            if ($rootScope.feedbacks) {
                angular.forEach($rootScope.feedbacks, function (value) {
                    aggregateData(value);
                    $scope.$apply();
                })
            }
        });

        $scope.$on('$ionicView.afterLeave', function (viewInfo, state) {
            $rootScope.feedbacks = [];
        });

        $scope.$on('$ionicView.loaded', function (viewInfo, state) {
            $scope.data = [];
        });

        $scope.$on('logout', function (viewInfo, state) {
            $scope.data = [];
        });



    }
})();
