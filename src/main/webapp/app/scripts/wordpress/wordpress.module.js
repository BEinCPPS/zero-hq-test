(function() {
	'use strict';

	angular
		.module('zerohqt.wordpress', [
			'ionic',
			'zerohqt.common'
		])
		.config(function($stateProvider) {
			$stateProvider
				.state('app.wordpress-articles', {
					url: '/wordpress-articles',
					views: {
						'menuContent': {
							templateUrl: 'scripts/wordpress/wordpress-articles.html',
							controller: 'WordpressArticlesController as vm'
						}
					}
				})
				.state('app.wordpress-article', {
					url: '/wordpress-articles/:articleId',
					views: {
						'menuContent': {
							templateUrl: 'scripts/wordpress/wordpress-article.html',
							controller: 'WordpressArticleController as vm'
						}
					}
				});
		});
})();
