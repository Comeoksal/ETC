function Queue(){
    this.waitNumber = 0;
    this.head = null;
    function Order(num, order){
        this.number = num;
        this.order = order;
        this.next = null;
    }
    this.insert = function(wNumber, order){
        var node = new Order(wNumber, order);
        if(this.head==null){this.head = node; return;}
        else if (this.head!=null){
            var n1 = this.head;
            while(n1.next!=null){
                n1 = n1.next;
            }
            n1.next = node;
        }
    }
    this.delete = function(){
        var n2 = this.head;
        this.head = this.head.next;
        return n2;
    }
    this.showAll = function(){
        var n3 = this.head;
        while(n3!=null){
            console.log(`Number : ${n3.number} Menu : ${n3.order}`);
            n3 = n3.next;
        }
    }
}

var Q = new Queue();
Q.insert(0, "Steak");
Q.insert(1, "Pizza");
Q.insert(2, "Spagetti");
Q.insert(3, "Spagetti");
Q.insert(4, "Spagetti");
Q.insert(5, "Spagetti");
Q.delete();
Q.delete();
Q.delete();
Q.showAll();