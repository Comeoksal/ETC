function pro1(){
    return new Promise(function(resolve, reject){
        setTimeout(function(){
            resolve('pro 1sucess');
        }, 1000);
        setTimeout(function(){
            reject('pro1 error');
        }, 1000);
    }
    );
}
function pro2(){
    return new Promise(function(resolve, reject){
        setTimeout(function(){
            resolve('pro 2sucess');
        }, 10000);
    }
    );
}


pro1().then(function a(man){
    console.log("result", man);
})
.catch(function (err){
    console.log("err", err);
})
pro2().then(function a(result){
    console.log("result", result);
})