(function () {
    'use strict';

    angular
        .module('zerohqt.menu')
        .controller('MenuController', MenuController);

    MenuController.$inject = ['$scope','websocketService'];

    /* @ngInject */
    function MenuController($scope, websocketService) {

    }
})();
