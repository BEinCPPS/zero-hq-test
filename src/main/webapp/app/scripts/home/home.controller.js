(function () {
	'use strict';

	angular
		.module('zerohqt.home')
		.controller('HomeController', HomeController);

	HomeController.$inject = ['menuItems', 'homeDataService', 'externalAppsService',
		'$cordovaEmailComposer', 'websocketService', '$scope'];

	/* @ngInject */
	function HomeController(menuItems, homeDataService, externalAppsService, $cordovaEmailComposer, websocketService, $scope) {
		var messageList = [];
		var vm = angular.extend(this, {
			entries: messageList,
			phoneNumber: homeDataService.phoneNumber,
			getDirections: getDirections,
			sendEmail: sendEmail,
			openFacebookPage: openFacebookPage
		});

		$scope.$on('wSockMessage', function (event, message) {
			console.log(message); // 'Broadcast!'
			messageList.push(message);
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
