(function() {
	'use strict';

	angular
		.module('zerohqt.native-scrolling', [
			'ionic',
			'zerohqt.common'
		])
		.config(function($stateProvider) {
			$stateProvider
				.state('app.native-scrolling', {
					url: '/native-scrolling',
					views: {
						'menuContent': {
							templateUrl: 'scripts/native-scrolling/native-scrolling.html',
							controller: 'NativeScrollingController as vm'
						}
					}
				});
		});
})();
