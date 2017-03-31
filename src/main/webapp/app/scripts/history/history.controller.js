(function () {
    'use strict';

    angular
        .module('zerohqt.history')
        .controller('HistoryController', HistoryController);

    HistoryController.$inject = ['$scope', 'daoService', '$ionicLoading'];

    /* @ngInject */
    function HistoryController($scope, daoService, $ionicLoading) {


        var vm = angular.extend(this, {});


//     TODO  Agganciare la station bay selezionata sulla pagina html
       /*var stationBay = 'TestStation1_1';
        $scope.search = function (stationBay) {
              daoService.stationBayHistory(stationBay).then(function (req) {
                $scope.notifications = req.data;
               }, function (err) {
                   console.log(err);
                });}; */

        ($scope.listStationsBays = function () {
            daoService.fullStationsBays().then(function (req) {
               var all = [{'All':''}];
//               $scope.stationsBays = all.concat(req.data);
//               $scope.filter = $scope.stationsBays[0];


               function splitta(value) {
                  var values = value.split("_")
                  return values[2] + ' ' + values[3]
                                };

               $scope.stationsBays = [];
               $scope.stationsBays[0] = {label:'All',value:'All'};
               for (var i = 0 ; i < req.data.length; i++) {
                    var labe = splitta(req.data[i]);
                    $scope.stationsBays[(i+1)] = {
                        label: labe,
                        value: req.data[i]
                     };
               }
                $scope.filter = $scope.stationsBays[0];


            }, function (err) {
                console.log(err);
            });
        })();


        $scope.loadMore = function () {
            //$scope.show($ionicLoading);
            daoService.fullHistory().then(function (req) {
                $scope.notifications = req.data;
                //$scope.$broadcast('scroll.infiniteScrollComplete');
                //$scope.hide($ionicLoading);
            }, function (err) {
                console.log(err);
                //$scope.hide($ionicLoading);
            });
        }
        $scope.show = function () {
           alert("w maria");
        };

       /* $scope.hide = function () {
            $ionicLoading.hide();
        };*/
//        $scope.$on('wsMessage', function (event, informationBay) {
//            notifications.push(informationBay);
////            $scope.$apply(); //Apply changes to the page
//        });

//        $scope.$on('$stateChangeSuccess', function () {
//            $scope.loadMore();
//        });
 $scope.loadMore();
    }
})();
