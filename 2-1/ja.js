var str = 'Hello World'
for(let a = 0; a<str.length; a++){
    console.log(str.charAt(a));
}

function Test(n){
    this.name = {
        first : "m",
        last : "k"
    };
    this.age=30,
    this.show = function() {console.log(this.name.first + "," + this.age);}
};
function showw() {
    console.log('3');
}
let t = new Test("Kim");

t.show();

const readline = require("readline");
const rl = readline.createInterface({
    input : process.stdin,
    output : process.stdout,
});

let dic = {
    boy : "소년",
    girl : "소녀",
    friend : "친구"
};
const age = 30;

rl.question("찾을 단어를 입력하세요 : ", function(key){
    let word = key;
    console.log(dic[word]);

    rl.close();
})