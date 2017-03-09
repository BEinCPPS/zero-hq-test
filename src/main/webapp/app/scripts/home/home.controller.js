(function () {
    'use strict';

    angular
        .module('zerohqt.home')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['menuItems', 'homeDataService', 'externalAppsService',
        '$cordovaEmailComposer', 'websocketService', '$scope'];

    /* @ngInject */
    function HomeController(menuItems, homeDataService, externalAppsService, $cordovaEmailComposer, websocketService, $scope) {
        var messageMap = {};
        var vm = angular.extend(this, {
            entries: messageMap
        });

        $scope.$on('wsMessage', function (event, informationBay) {
            console.log(informationBay); // 'Broadcast!'
            aggregateData(informationBay);
            $scope.$apply(); //Apply changes to the page
        });

        /*function getDirections() {
            externalAppsService.openMapsApp(homeDataService.officeLocation);
        }

        function sendEmail() {
            $cordovaEmailComposer.isAvailable().then(function () {
                var email = {
                    to: homeDataService.email,
                    subject: 'Cordova Icons',
                    body: 'How are you? Nice greetings from Leipzig'
                };

                $cordovaEmailComposer.open(email);
            });
        }

        function openFacebookPage() {
            externalAppsService.openExternalUrl(homeDataService.facebookPage);
        }*/

        function aggregateData(informationBay) {
            var stationName = informationBay.stationName;
            var baies = messageMap[stationName] ? messageMap[stationName] : [];
            var bayNumber = parseInt(informationBay.bayNumber);
            bayNumber--;
            baies[bayNumber] = informationBay; //Station baies numbers starts from 1
            messageMap[stationName] = baies;
        }

        (function connectWebSocket() {
            websocketService.connect();
        })();

    }
})();
