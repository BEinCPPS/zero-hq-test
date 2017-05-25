'use strict';
angular.module('hideBack', [])


// - Documentation: https://forum.ionicframework.com/t/hide-ion-nav-back-button/2569/2

    .directive('navClear', ['$ionicViewService', function ($ionicViewService) {
        return {
            restrict: 'AC',
            link: function ($scope, $element, $attr) {
                $element.bind('click', function () {
                    $ionicViewService.nextViewOptions({
                        disableAnimate: true,
                        disableBack: true
                    });
                });
            }
        };
    }]);