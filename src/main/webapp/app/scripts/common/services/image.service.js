(function () {
    'use strict';

    angular
        .module('zerohqt.common')
        .factory('imageService', imageService);

    imageService.$inject = ['$window', 'ENV'];

    /* @ngInject */
    function imageService($window, ENV) {
        var service = {
            getImage: getImage,
            getDefaultImage:getDefaultImage
        };
        return service;

        // ******************************************************
        function getDefaultImage(imageName){
            return ENV.imagesFolder + "xhdpi/" + imageName;
        }

        function getImage(imageName) {
            if (!ionic.Platform.isAndroid()) return null;
            switch ($window.devicePixelRatio) {
                case '0.75':
                    var density = 'ldpi';
                    break;
                case '1':
                    var density = 'mdpi';
                    break;
                case '1.5':
                    var density = 'hdpi';
                    break;
                default:
                    var density = 'xhdpi';
                    break;
            }
            console.log('La densità è:' + density);
            return ENV.imagesFolder + density + "/" + imageName;
        }
    }
})();
