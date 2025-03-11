const readline = require("readline");
const rl = readline.createInterface({
    input:process.stdin,
    output:process.stdout,
});

rl.question("1)register 2)login 3)see all user >>"), function(nai){
    if(nai>19){
    console.log("입장");
    }
};
