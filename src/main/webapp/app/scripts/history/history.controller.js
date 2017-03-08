(function() {
	'use strict';

	angular
		.module('zerohqt.history')
		.controller('HistoryController', HistoryController);

		HistoryController.$inject = ['menuItems','$scope'];

	/* @ngInject */
	function HistoryController(menuItems, $scope) {
		var notifications = [] ;
		var vm = angular.extend(this, {
			notifications : notifications,
			loadMore: loadMore
			//TODO: add methods and properties to this controller
		});
        $scope.$on('wsMessage', function (event, informationBay) {
        	notifications.push(informationBay);
            $scope.$apply(); //Apply changes to the page
        });

        var loadMore = function () {

        }

	}
})();
