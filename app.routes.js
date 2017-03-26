angular
    .module('Predarvutus')
    .config(function config($routeProvider){
        $routeProvider
            .when('myresource', {
                templateUrl: 'app/views/index.html'
            })
    });