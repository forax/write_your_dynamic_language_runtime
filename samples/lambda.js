function test(message, to) {
  var lambda = function() { print(message, to); };
  lambda();
}

test("hello", "Bob");
