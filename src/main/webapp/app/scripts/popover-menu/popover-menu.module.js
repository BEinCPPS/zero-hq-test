(function() {
	'use strict';

	angular
		.module('zerohqt.popover-menu', [
			'ionic'
		])
		.config(function($stateProvider) {
			$stateProvider
				.state('app.popover-menu', {
					url: '/popover-menu',
					views: {
						'menuContent': {
							templateUrl: 'scripts/popover-menu/popover-menu.html',
							controller: 'PopoverMenuController as vm'
						}
					}
				});
		});
})();
