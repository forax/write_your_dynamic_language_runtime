var a = 4;

var o = {
    x: 1,
    y: 1 + a,
    t: function() {
        //return this.y + this.x;
        return this.x + this.y + a;
    },
    print: function () {
        print(this.x, this.y);
    }
};

print(o.x);
print(o.y);
o.print();
print(o.x + o.y);
print(o.t());

