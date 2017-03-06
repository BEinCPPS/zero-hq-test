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
							controller: 'HistoryController as vm'
						}
					}
				})
				.state('app.history-form-placeholder-labels', {
					url: '/history-form-placeholder-labels',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/form-placeholder-labels.html'
						}
					}
				})
				.state('app.history-form-inline-labels', {
					url: '/history-form-inline-labels',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/form-inline-labels.html'
						}
					}
				})
				.state('app.history-form-stacked-labels', {
					url: '/history-form-stacked-labels',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/form-stacked-labels.html'
						}
					}
				})
				.state('app.history-form-floating-labels', {
					url: '/history-form-floating-labels',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/form-floating-labels.html'
						}
					}
				})
				.state('app.history-form-inset-form', {
					url: '/history-form-inset-form',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/form-inset-form.html'
						}
					}
				})
				.state('app.history-form-inset-inputs', {
					url: '/history-form-inset-inputs',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/form-inset-inputs.html'
						}
					}
				})
				.state('app.history-form-input-icons', {
					url: '/history-form-input-icons',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/form-input-icons.html'
						}
					}
				})
				.state('app.history-toggle', {
					url: '/history-toggle',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/toggle.html'
						}
					}
				})
				.state('app.history-checkbox', {
					url: '/history-checkbox',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/checkbox.html'
						}
					}
				})
				.state('app.history-radiobuttons', {
					url: '/history-radiobuttons',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/radiobuttons.html'
						}
					}
				})
				.state('app.history-range', {
					url: '/history-range',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/range.html'
						}
					}
				})
				.state('app.history-select', {
					url: '/history-select',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/select.html'
						}
					}
				})
				.state('app.history-tabs-icononly', {
					url: '/history-tabs-icononly',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/tabs-icononly.html'
						}
					}
				})
				.state('app.history-tabs-topicon', {
					url: '/history-tabs-topicon',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/tabs-topicon.html'
						}
					}
				})
				.state('app.history-tabs-lefticon', {
					url: '/history-tabs-lefticon',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/tabs-lefticon.html'
						}
					}
				})
				.state('app.history-tabs-striped', {
					url: '/history-tabs-striped',
					views: {
						'menuContent': {
							templateUrl: 'scripts/history/tabs-striped.html'
						}
					}
				});
		});
})();
