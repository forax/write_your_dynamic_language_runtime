var o = {
  x: 1,
  y: 1 + 1,
  print: function () {
    print(this.x, this.y);
  }
};

print(o.x);
print(o.y);
o.y = 3;
print(o.y);
o.print();
