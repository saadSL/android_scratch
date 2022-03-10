cordova.define('cordova/plugin_list', function(require, exports, module) {
  module.exports = [{
                          "id": "com.unikrew.faceoff.ABLPlugin",
                          "file": "plugins/com.unikrew.faceoff/www/ABLPlugin.js",
                          "pluginId": "com.unikrew.faceoff",
                          "clobbers": [
                            "cordova.plugins.ABLPlugin"
                          ]
                        }];
  module.exports.metadata = {
    "cordova-plugin-whitelist": "1.3.5"
     "com.unikrew.faceoff": "0.1"
  };
});