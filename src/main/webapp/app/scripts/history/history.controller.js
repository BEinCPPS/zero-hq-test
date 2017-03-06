(function() {
	'use strict';

	angular
		.module('zerohqt.history')
		.controller('HistoryController', HistoryController);

		HistoryController.$inject = ['menuItems'];

	/* @ngInject */
	function HistoryController(menuItems) {
		var vm = angular.extend(this, {
			//TODO: add methods and properties to this controller
		});

	}
})();
