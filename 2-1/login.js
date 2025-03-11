
const reader = require('readline-sync');

var a = reader.question('1)register 2)login 3)see all user');

var user = [];
if(a==1){
    var flag =0;
    while(flag!=1){
    var id1 = reader.question('아이디 : ', encoding = 'UTF-8');
    for(var i=0; i<user.length; i++){
        if(id==user[i].id){console.log('아이디가 중복입니다. 다시 입력해주세요.' , encoding = 'UTF-8'); flag =0; break;}
        flag=1;
    }
}
    var pw1 = reader.question("비밀번호 : ");
    var name1 = reader.question('이름 : ');
    user.push(
        {
            id : id1,
            pw : pw1,
            name : name1,
            show : function(){
                return `아이디 : ${this.id} 사용자 이름 : ${this.name}<BR>`
            }
        }
    )
}
