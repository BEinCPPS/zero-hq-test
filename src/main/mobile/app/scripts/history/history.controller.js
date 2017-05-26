(function () {
    'use strict';

    angular
        .module('zerohqt.history')
        .controller('HistoryController', HistoryController);

    HistoryController.$inject = ['$scope', 'daoService', '$ionicLoading', '$ionicScrollDelegate'];

    /* @ngInject */
    function HistoryController($scope, daoService, $ionicLoading, $ionicScrollDelegate) {
        $scope.notifications = [];
        $scope.isDataArrived = false;
        $scope.scrollTop = function () {
            $scope.scrollMainToTop = function () {
                $ionicScrollDelegate.scrollTop();
                $scope.sttButton=false;
            };
        };


        $scope.$on('$ionicView.loaded', function (viewInfo, state) {
            $scope.listStationsBays();
            $scope.loadMore();
        });

        $scope.listStationsBays = function () {
            daoService.fullStationsBays().then(function (req) {
                var all = [{'All': ''}];

                function splitta(value) {
                    var values = value.split("_")
                    return 'Station: ' + values[2] + ' - Bay: ' + values[3];
                };
                $scope.stationsBays = [];
                $scope.stationsBays[0] = {label: 'All', value: 'All'};
                $scope.stationsBays[1] = {label: 'Feedback', value: 'Feedback'};
                for (var i = 0; i < req.data.length; i++) {
                    var labe = splitta(req.data[i]);
                    $scope.stationsBays[(i + 2)] = {
                        label: labe,
                        value: req.data[i]
                    };
                }
                $scope.filter = $scope.stationsBays[0];
            }, function (err) {
                console.log(err);
            });
        };

        $scope.loadMore = function () {
            daoService.fullHistory().then(function (req) {
                $scope.notifications = req.data;
                $scope.isDataArrived = true;
                $scope.$broadcast('scroll.refreshComplete');
            }, function (err) {
                console.log(err);
            });
        };

        $scope.$on('$ionicView.enter', function (viewInfo, state) {
          //  $scope.loadMore();
        });

        $scope.$on('$ionicView.afterLeave', function (viewInfo, state) {
          //  $scope.isDataArrived = false;
        });

        $scope.searchByStationBay = function (stationBayExtended) {
            if (stationBayExtended.value == 'All'
                || typeof  stationBayExtended === 'undefined'
                || stationBayExtended === null) {
                daoService.fullHistory().then(function (req) {
                    $scope.notifications = req.data;
                }, function (err) {
                    console.log(err);
                });
            }
            else {
                if (stationBayExtended.value == 'Feedback') {
                    daoService.fullHistoryByAck('ack4').then(function (req) {
                        $scope.notifications = req.data;
                    }, function (err) {
                        console.log(err);
                    });
                }
                else {
                    var values = stationBayExtended.value.split("_");
                    var item = values[2] + '_' + values[3];
                    daoService.stationBayHistory(item).then(function (req) {
                        $scope.notifications = req.data;
                    }, function (err) {
                        console.log(err);
                    });
                }
            }
        }
    }
})();
