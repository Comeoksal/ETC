function Node (n, g){
    this.name = n;
    this.grade = g;
    this.next = null;
    this.getName = function(){
        return this.name;
    }
}
function LList(){
    this.first = null;
    this.findLastNode = function(){
        var node = this.first;
        if(node==null) return null;
        while(node.next != null){
            node = node.next;
        }
        return node;
    }
    this.insert = function(n, g){
        var n1 = new Node(n, g);
        var n2 = this.findLastNode();
        if(n2==null) {this.first = n1;}
        else{
            n2.next = n1;
        }
    }
    this.showAll = function(){
        var n1 = this.first;
        while(n1!=null){
            console.log(n1.getName() + '->');
            n1 = n1.next;
        }
    }
}
function STACK(){
    var sList = new Array();
    this.top=-1;
    this.push = function(name, age){
        this.top++;
        sList[this.top] = {
            name : name,
            age : age,
        };
    }
    this.pop = function(){
        this.top--;
    }
    this.digit = function(a){
        for(var i=0; i<sList.length; i++){
            if(a==sList[i].name) {
                sList[i].name = "NaN"
                sList[i].age = 0;
            }
        }
    }
    this.show = function(){
        if(this.top==-1){console.log('List is Empty!'); return;}
        console.log(`Name : ${sList[this.top].name} Age : ${sList[this.top].age}`);
    }
}
function Queue(){
    this.front = -1;
    this.rear = -1;
    var qList = new Array(5);
    this.add = function(a){
        if(this.rear==4){return;}
        this.rear++;
        qList[this.rear] = {
            name : a
        };
    }
    this.out = function(){
        if(this.front==this.rear) {return;}
        this.front++;
    }
    this.show = function(){
        if(this.front==this.rear) {console.log('Queue is Empty!'); return;}
        else if(this.rear==5){console.log('Queue is Full!'); return;}
        this.front++;
        console.log(`Name : ${qList[this.front].name}`);
        this.front--;
    }
}
// var list = new LList();
// list.insert('Kim', 3);
// list.insert('Pim', 3);
// list.insert('Jim', 6);
// console.log(list.showAll());

// var stack = new STACK();
// stack.push("keum", 36);
// stack.push("kim", 12);
// stack.push("jeong", 18);
// stack.push("lee", 23);
// stack.push("park", 21);
// stack.digit("jeong");
// stack.show();
// stack.pop();
// stack.show();
// stack.pop();
// stack.show();
var Q = new Queue();
Q.add("keum");
Q.add("kim");
Q.add("jeong");
Q.out();
Q.out();
Q.out();
Q.add("min");
Q.out();
Q.add("odd");
Q.out();
Q.show();