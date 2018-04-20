import Vue from 'vue'




Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分 cd
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

Date.prototype.toISOString = function () {
    return this.Format("yyyy-MM-dd HH:mm:ss")
}

String.prototype.extName = function () {
    var arrfn = this.split(".");
    return "." + arrfn[arrfn.length - 1];
}

Array.prototype.contains = function (needle) {
    for (i in this) {
        if (this[i] == needle) return true;
    }
    return false;
}

String.prototype.httpPath = function (map) {
    let keys = Object.keys(map)
    let args = "?"
    keys.forEach(function (key) {
        args += key;
        args += "="
        args += encodeURIComponent(map[key])
        args += "&"
    })
    args = args.substr(0, args.length - 1)

    return this + args
}
const deepCopy = (data) => {
    let node;
    if (Array.isArray(data)) {
        node = data.length > 0 ? data.slice(0) : [];
        node.forEach((e, i) => {
            if (
                (typeof e === "object" && e !== {}) ||
                (Array.isArray(e) && e.length > 0)
            ) {
                node[i] = deepCopy(e);
            }
        });
    } else if (typeof data === "object") {
        node = Object.assign({}, data);
        Object.keys(node).forEach((key) => {
            if (
                (typeof node[key] === "object" && node[key] !== {}) ||
                (Array.isArray(node[key]) && node[key].length > 0)
            ) {
                node[key] = deepCopy(node[key]);
            }
        });
    } else {
        node = data;
    }
    return node;
};
Vue.prototype.deepCopy = deepCopy;

Vue.prototype.intervalFlag = false;

Vue.prototype.getToApi = function (path, form, successCallback, errorCallback) {
    var httpUri = "api/jersey/" + path.httpPath(form);
    this.$http.get(httpUri, {timeout: 10000,currentPage:form.currentPage, pageSize:form.pageSize}).then(
        (res) => {
          successCallback(res.data)
        },(err) => {
        errorCallback(err)
      })
}

Vue.prototype.postToApi = function (path, form, successCallback, errorCallback) {
    var httpUri = "/api/jersey/" + path
    if (form) {
        httpUri = httpUri.httpPath(form)
    }
    this.$http.post(httpUri, form).then(
        (res) => {
          successCallback(res.data)
        },err=>{
        errorCallback({err:err,errorCode:err.response.status,message:err.message})
        })
}

Vue.prototype.putToApi = function (path, form, successCallback, errorCallback) {
    var httpUri = "/api/jersey/" + path
    if (form) {
        httpUri = httpUri.httpPath(form)
    }
    this.$http.put(httpUri, form).then(
        (res) => {
          successCallback(res.data)
        }, (err) => {
        errorCallback(err);
        })
}
Vue.prototype.delToApi = function (path, form, successCallback, errorCallback) {
    var httpUri = "/api/jersey/" + path
    if (form) {
        httpUri = httpUri.httpPath(form)
    }
    this.$http({
        method: 'DELETE',
        url: httpUri,
        data: form
    }).then(
        (res) => {
          successCallback(res.data)
        },(err) => {
        errorCallback(err);
      });
};
Vue.prototype.getCooke = function (c_name) {
  if (document.cookie.length>0)
  {
    let c_start=document.cookie.indexOf(c_name + "=");
    if (c_start!=-1)
    {
      c_start=c_start + c_name.length+1;
      let c_end=document.cookie.indexOf(";",c_start);
      if (c_end==-1) c_end=document.cookie.length;
      return unescape(document.cookie.substring(c_start,c_end))
    }
  }
  return ""
};

