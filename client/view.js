getData = npwp =>
    axios
        .get('http://127.0.0.1:8080/person')
        .then(res => console.log(res))

getData();

var app = angular.module('pajakin', ['ngRoute'])
    .config(($routeProvider) => {
        $routeProvider
            .when("/", {
                templateUrl : "./login.html"
            })
            .when("/login", {
                templateUrl : "./login.html"
            })
            .when("/home", {
                templateUrl: './userlist.html',
            })
            .when("/input", {
                templateUrl: './userinput.html',
            })
            .when("/property", {
                templateUrl: './propertyinput.html'
            })
            .when("/vehicle", {
                templateUrl: './vehicleinput.html'
            })
    });

app.controller('pajakin-login', ($window, $scope, $rootScope) => {
    $scope.login = () => {
        if ($scope.username === 'danu' && $scope.password === 'eko') {
            $rootScope.logged = true;
            $window.location.href = '#!/home'
        } else {
            $rootScope.logged = false;
        }
    }
});

app.controller('pajakin-home', ($http, $window, $scope, $rootScope, $route) => {
    if (!$rootScope.logged) {
        $window.location.href = '#!/login'
    }
    $http.get('http://localhost:8080/person')
        .then(res => $scope.data = res.data)
        .catch(err => console.log(err))

    $scope.select = (npwp) => {
                $scope.pph = 0;
                $scope.pbb = 0;
                $scope.pkb = 0;
 
        $scope.selected = $scope.data.find(d => d.npwp === npwp)
        $http
            .get(`http://localhost:8080/person/${npwp}/tax`)
            .then(res => {
                res.data.forEach(d => {
                    if (d.type === 'class pajakin.model.PPh') {
                        $scope.pph += d.val;
                    } else if (d.type === 'class pajakin.model.PBB') {
                        $scope.pbb += d.val
                    } else if (d.type === 'class pajakin.model.PKB') {
                        $scope.pkb += d.val
                    }
                    console.log(res)
                })
            })
            .catch(err => alert(err));
        $http
            .get(`http://localhost:8080/person/${npwp}/vehicle`)
            .then(res => {
                console.log(res);
                $scope.selectedVehicle = res.data;
            })
            .catch(err => alert(err))
        $http
            .get(`http://localhost:8080/person/${npwp}/property`)
            .then(res => {
                console.log(res);
                $scope.selectedProperty = res.data;
            })
            .catch(err => alert(err))
    };

    $scope.toEdit = npwp => {
        $rootScope.toBeEdited = npwp;
        $window.location.href = '#!/input'
    }

    $scope.addNew = () => {
        $rootScope.toBeEdited = undefined;
        $window.location.href = '#!/input'
    }

    $scope.delete = npwp => {
        $http.post(`http://localhost:8080/person/delete/${npwp}`);
        console.log(npwp)
        $route.reload();
    }

    $scope.addV = npwp => {
        $rootScope.vNpwp = npwp;
        $window.location.href = '#!/vehicle'
    }

    $scope.addP = npwp => {
        $rootScope.pNpwp = npwp;
        $window.location.href = '#!/property'
    }
})

app.controller('person-input', ($rootScope, $window, $http, $scope) => {
    $scope.editMode = false;
    if ($rootScope.toBeEdited !== undefined) { 
        $scope.editMode = true;
    }
    if (!$rootScope.logged) {
        $window.location.href = '#!/login'
    }
    $scope.submit = () => {
        let {fullname, npwp, nik, salary, allowance, marital, child} = $scope;
        if (marital === undefined) {
            marital = false;
        }
        if ($scope.editMode) {
            $http
                .post(`http://localhost:8080/person/edit/${fullname}/${nik}/${$rootScope.toBeEdited}/${salary}/${allowance}/${marital}/${child}`)
                .then(() => $window.location.href = '#!/home')
        } else {
            $http
                .post(`http://localhost:8080/person/add/${fullname}/${nik}/${npwp}/${salary}/${allowance}/${marital}/${child}`)
                .then(() => $window.location.href = '#!/home')
        }
    }
})

app.controller('vehicle', ($rootScope, $window, $http, $scope) => {
    if (!$rootScope.logged) {
        $window.location.href = '#!/login'
    }
    
    $scope.submit = () => {
        const {plate, value} = $scope;

        $http
            .post(`http://localhost:8080/person/${$rootScope.vNpwp}/vehicle/add/${plate}/${value}`)
            .then(() => $window.location.href = '#!/home')
    }
})

app.controller('property', ($rootScope, $window, $http, $scope) => {
    if (!$rootScope.logged) {
        $window.location.href = '#!/login'
    }
    
    $scope.submit = () => {
        const {address, landArea, landVal, buildArea, buildVal} = $scope;

        $http
            .post(`http://localhost:8080/person/${$rootScope.pNpwp}/property/add/${address}/${landArea}/${landVal}/${buildArea}/${buildVal}`)
            .then(() => $window.location.href = '#!/home')
    }
})