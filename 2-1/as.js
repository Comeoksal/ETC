const rl = require('readline-sync') ;
let a=0;
let student = new Array();
// let dic = {
//     boy : "소년",
//     girl : "소녀",
//     friend : "친구",
// };
// for(let info in dic){
//     console.log(`${info} :: ${dic[info]}`);
// }
function ES(n, s){
    this.name = 0;
    student.push(
        {
            sn : num,
            ss : score,
            show : function (){
                return (`Student Number : ${this.sn} Student Score : ${this.ss} `);
            }
        }
    )
};
function SAS(){
    if(student.length==0) {console.log("Can't load student list."); return;}
    else{
    for(var i=0; i<student.length-1; i++){
        for(var j=i+1; j<student.length; j++){
            if(student[i].ss<student[j].ss){
                var temp1 = student[j].ss;
                student[j].ss = student[i].ss;
                student[i].ss = temp1;

                var temp2 = student[j].sn;
                student[j].sn = student[i].sn;
                student[i].sn = temp2;
            }
        }
    }
    for(var i=0; i<student.length; i++){
        console.log(student[i].show());
    }
}
}
while(true){
    a = rl.question("1)Enter Score 2)See All Score 3)EXIT >> ");
    if(a==1){
        var num = (Number)(rl.question("Enter your school number : "));
        var score = (Number)(rl.question("Enter your score : "));
        ES(num, score);
    }
    else if(a==2){
        SAS();
    }
    else if(a==3) {
        break;
    }
}