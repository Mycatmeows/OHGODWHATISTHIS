(
    function(){       
    
 
        var app = angular.module('index', []);

        app.controller('indexController', function(){
            this.Users = testUsers;
        });

        var testUsers=[
        {
            name:'dummy',
            password_hash:'FFFFFF',
        },
        {
            name:'dummerson',
            passowrd_hash:"AAAAAA",    
        },
            
        ];
    
    
    }
)();