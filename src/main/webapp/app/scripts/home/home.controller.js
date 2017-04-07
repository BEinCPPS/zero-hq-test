(function () {
    'use strict';

    angular
        .module('zerohqt.home')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['websocketService', '$scope'];

    /* @ngInject */
    function HomeController(websocketService, $scope) {
        var messageMap = {};
        var vm = angular.extend(this, {
            entries: messageMap
        });

        $scope.$on('wsMessageBay', function (event, informationBay) {
            console.log(informationBay); // 'Broadcast!'
            aggregateData(informationBay);
            $scope.$apply(); //Apply changes to the page
        });

        function aggregateData(informationBay) {
            var stationName = informationBay.stationName;
            var bays = messageMap[stationName] ? messageMap[stationName] : [];
            initBaysArray(bays);
            var bayNumber = parseInt(informationBay.bayNumber);
            bayNumber--;
            bays[bayNumber] = informationBay; //Station bays numbers starts from 1
            messageMap[stationName] = bays;
        }

        function initBaysArray(bays) {
            for (var i in bays) {
                if (bays[i]) continue;
                else bays[i] = {};
            }
        }

        (function connectWebSocket() {
            websocketService.connect();
        })();

    }
})();
