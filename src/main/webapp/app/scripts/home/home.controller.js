(function () {
    'use strict';

    angular
        .module('zerohqt.home')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$rootScope', 'imageService'];

    /* @ngInject */
    function HomeController($scope, $rootScope, imageService) {
        $scope.entries = {};
        $scope.MAX_NR_BAYS = 4;
        imageService.getImage();

        $scope.getImageUrl = function (imageName, isDefault) {

            return !isDefault ? imageService.getImage(imageName) : imageService.getDefaultImage(imageName);
        }


        $scope.$on('wsMessage', function (event, informationBay) {
            console.log(informationBay); // 'Broadcast!'
            aggregateData(informationBay);
            $scope.$apply(); //Apply changes to the page
        });

        function aggregateData(informationBay) {
            var stationName = informationBay.stationName;
            var bays = $scope.entries[stationName] ? $scope.entries[stationName] : [];
            initBaysArray(bays);
            var bayNumber = parseInt(informationBay.bayNumber);
            bayNumber--;
            bays[bayNumber] = informationBay; //Station bays numbers starts from 1
            $scope.entries[stationName] = bays;
        }

        function initBaysArray(bays) {
            for (var i = 0; i < $scope.MAX_NR_BAYS; i++) {
                if (bays[i]) continue;
                bays[i] = {};
            }
        }

        $scope.getBackgroundColor = function (stateType) {
            if (stateType === 'normal')  return 'button icon ion-android-checkmark-circle green-button';
            else if (stateType === 'warning') return 'button icon ion-android-warning yellow-button';
            else if (stateType === 'error') return 'button icon ion-android-alert red-button';

        }

        $scope.getBottomHeightPanelStyle = function () {
            if (window.screen.height <= 700)
                return 'bottom-low'
            else if (window.screen.height >= 700 && window.screen.height <= 1000)
                return 'bottom-medium';
            else if (window.screen.height > 1000)
                return 'bottom-high';
        }

        $scope.$on('$ionicView.loaded', function (viewInfo, state) {
        });

        $scope.$on('$ionicView.enter', function (viewInfo, state) {
            if ($rootScope.informationBays) {
                angular.forEach($rootScope.informationBays, function (value) {
                    aggregateData(value);
                    $scope.apply();
                })
            }
            if (!$rootScope.isWsConnected) {
                $scope.entries = {};
            }
        });

        $scope.$on('$ionicView.afterLeave', function (viewInfo, state) {
            $rootScope.informationBays = [];
        });

        /*var listenerCleanFn = $scope.$on('wsMessage', function () {

         });

         $scope.$on('$destroy', function() {
         listenerCleanFn();
         });*/


    }
})();
