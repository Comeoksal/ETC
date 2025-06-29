x= 10 

def outer(): 
    x = 20 
    
    def inner(): 
        print(x) 

    inner() 
    x = 30 
    inner() 

outer() 
print(x) 