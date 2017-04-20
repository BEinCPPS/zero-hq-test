(function () {
    'use strict';

    angular
        .module('zerohqt.history')
        .controller('HistoryController', HistoryController);

    HistoryController.$inject = ['$scope', 'daoService', '$ionicLoading'];

    /* @ngInject */
    function HistoryController($scope, daoService, $ionicLoading) {
       // var vm = angular.extend(this, {});
        $scope.notifications = [];
        $scope.$on('$ionicView.loaded', function (viewInfo, state) {
            $scope.listStationsBays();
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
                for (var i = 0; i < req.data.length; i++) {
                    var labe = splitta(req.data[i]);
                    $scope.stationsBays[(i + 1)] = {
                        label: labe,
                        value: req.data[i]
                    };
                }
                $scope.filter = $scope.stationsBays[0];


            }, function (err) {
                console.log(err);
            });
        }

//     TODO  gestire el diverse situazioni con fullHistory o con searchByStationBay
        $scope.loadMore = function () {
            daoService.fullHistory().then(function (req) {
                $scope.notifications = $scope.notifications.concat(req.data);
                $scope.$broadcast('scroll.infiniteScrollComplete');
            }, function (err) {
                console.log(err);
            });
        }
        $scope.searchByStationBay = function (stationBayExtended) {
            if (stationBayExtended.value == 'All') {
                daoService.fullHistory().then(function (req) {
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
})();
