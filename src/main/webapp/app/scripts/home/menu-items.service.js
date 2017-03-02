(function() {
	'use strict';

	angular
		.module('zerohqt.home')
		.factory('menuItems', menuItems);

	menuItems.$inject = [];

	/* @ngInject */
	function menuItems() {
		var data = [{
			title: 'Station One',
			path: 'popover-menu',
			icon: 'ion-ios-arrow-down'
		}, {
			title: 'Station Two',
			path: 'wordpress-articles',
			icon: 'ion-social-wordpress'
		}, {
			title: 'Station Three',
			path: 'elements',
			icon: 'ion-android-checkbox-outline'
		}, {
			title: 'Station For',
			path: 'map',
			icon: 'ion-map'
		}];

		return data;
	}
})();
