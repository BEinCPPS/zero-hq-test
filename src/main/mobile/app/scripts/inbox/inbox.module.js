(function() {
	'use strict';

	angular
		.module('zerohqt.inbox', [
			'ionic',
			'zerohqt.common'
		])
		.config(function($stateProvider) {
			$stateProvider
				.state('app.inbox', {
					url: '/inbox',
					views: {
						'menuContent': {
							templateUrl: 'scripts/inbox/inbox.html',
							controller: 'InboxController'
						}
					}
				})
		});
})();
