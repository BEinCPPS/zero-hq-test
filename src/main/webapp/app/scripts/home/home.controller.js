(function () {
	'use strict';

	angular
		.module('zerohqt.home')
		.controller('HomeController', HomeController);

	HomeController.$inject = ['menuItems', 'homeDataService', 'externalAppsService', '$cordovaEmailComposer', 'websocketService'];

	/* @ngInject */
	function HomeController(menuItems, homeDataService, externalAppsService, $cordovaEmailComposer, websocketService) {
		var vm = angular.extend(this, {
			entries: menuItems,
			phoneNumber: homeDataService.phoneNumber,
			getDirections: getDirections,
			sendEmail: sendEmail,
			openFacebookPage: openFacebookPage
		});

		function getDirections() {
			externalAppsService.openMapsApp(homeDataService.officeLocation);
		}

		function sendEmail() {
			$cordovaEmailComposer.isAvailable().then(function () {
				var email = {
					to: homeDataService.email,
					subject: 'Cordova Icons',
					body: 'How are you? Nice greetings from Leipzig'
				};

				$cordovaEmailComposer.open(email);
			});
		}

		function openFacebookPage() {
			externalAppsService.openExternalUrl(homeDataService.facebookPage);
		}

		(function connectWebSocket() {
			websocketService.connect();
		})();

	}
})();
