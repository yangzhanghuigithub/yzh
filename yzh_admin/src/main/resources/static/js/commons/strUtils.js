/**
 * 字符串相关处理工具类
 * */
var StringUtils=(function () {
  return {
      /**
       * 获取字符串的长度（英文1，汉字2）
       * @param str
       * @return {number}
       */
      "getLength":function(str) {
          ///<summary>获得字符串实际长度，中文2，英文1</summary>
          ///<param name="str">要获得长度的字符串</param>
          var realLength = 0, len = str.length, charCode = -1;
          for (var i = 0; i < len; i++) {
              charCode = str.charCodeAt(i);
              if (charCode >= 0 && charCode <= 128)
                  realLength += 1;
              else
                  realLength += 2;
          }
          return realLength;
      }
  };
})();