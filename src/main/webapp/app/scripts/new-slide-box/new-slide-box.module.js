(function() {
	'use strict';

	angular
		.module('zerohqt.new-slide-box', [
			'ionic',
			'zerohqt.common'
		])
		.config(function($stateProvider) {
			$stateProvider
				.state('app.new-slide-box', {
					url: '/new-slide-box',
					views: {
						'menuContent': {
							templateUrl: 'scripts/new-slide-box/new-slide-box.html',
							controller: 'NewSlideBoxController as vm'
						}
					}
				});
		});
})();
