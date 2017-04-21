(function() {
	'use strict';

	angular
		.module('zerohqt.home', [
			'ionic',
			'ngCordova',
			'zerohqt.common'
		])
		.config(function($stateProvider) {
			$stateProvider
				.state('app.home', {
					url: '/home',
					views: {
						'menuContent': {
							templateUrl: 'scripts/home/home.html',
							controller: 'HomeController'
						}
					}
				});
		});
})();
