const rl = require('readline-sync') ; 

let user = new Array();
function register(){
    var flag =0;
    while(flag!=1){
        var id1 = rl.question('Id : ');
        if(user.length!=0){
        for(var i=0; i<user.length; i++){
         if(id1==user[i].id){console.log('The ID Check is failed Re enter ID.'); flag =0; break;}
         else {flag=1;}
        }
    }
    else flag=1;
    }
    var pw1 = rl.question('Password : ');
    var name1 = rl.question('Name : ');
    user.push(
        {
            id : id1,
            pw : pw1,
            name : name1,
            show : function(){
                return `ID : ${this.id} User Name : ${this.name}`
            }
        }
    )
}
function login(){
    var idflag=0;
    var pwflag=0;
    var id1 = rl.question('Id : ');
    var pw1 = rl.question('Password : ');
    if(user.length!=0){
        for(var i=0; i<user.length; i++){
         if(id1==user[i].id){idflag=1;  break;}
         else {flag=0;}
        }
        for(var i=0; i<user.length; i++){
            if(pw1==user[i].pw){pwflag=1;  break;}
            else {flag=0;}
           }
    }
    else{
        console.log("No User in our site.");
    }
    if(idflag==1&&pwflag==1){
        console.log("Welcome.");
    }
    else{
        console.log("Login error");
    }
}
function alluser(){
    if(user.length!=0){
        for(var i=0; i<user.length; i++){
            console.log(user[i].show());
        }
    }
    else{
        console.log("No User in our site.");
    }
}
while(a!=4){
var a = rl.question('1)register 2)login 3)see all user 4)EXIT');
if(a==1){
   register();
}
if(a==2){
    login();
}
if(a==3){
    alluser();
}
}