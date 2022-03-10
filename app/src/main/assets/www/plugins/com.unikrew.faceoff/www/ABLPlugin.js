cordova.define("com.unikrew.faceoff.ABLPlugin", function(require, exports, module) {
var exec = require("cordova/exec");

exports.startFlow = function (success, error) {
  exec(success, error, "ABLPlugin", "startFlow", []);
};

});
