   
        
        var dbg="Hello World!";
 
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
            password_hash:"AAAAAA",    
        },
        {
            name:'dummest',
            password_hash:'huehue',
        },
            
        ];

