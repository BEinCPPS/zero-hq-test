(function() {
	'use strict';

	angular
		.module('zerohqt.history', [
			'ionic',
			'ngCordova'
		])
		.config(function($stateProvider) {
			$stateProvider
				.state('app.history', {
					url: '/history',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/history.html',
							controller: 'HistoryController'
						}
					}
				})
		});
})();
