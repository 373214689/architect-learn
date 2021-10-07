/**
 * Common-api
 * Name: com
 */


console.log("loadding");

"use strict";

var selectorExpr = /^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]+))$/;

var rootElement = document;

var Common = window.Common ? window.Common : {};

var ajax = window.ajax = Common.ajax = function(option) {
    var _option = option ? option : {};
    var method

    var xmlHttpRequest;
    if (window.XMLHttpRequest) {
        xmlHttpRequest = new XMLHttpRequest();
    } else {
        XMLHttpRequest = new window.ActiveXObject("XMLHttpRequest");
    }



};

// 查找
var find = window.find = Common.find = function(selector, root) {
    "use strict";
    
    if (!selector || selector === undefined || selector == null) {
        return document.body;
    }
    if (typeof selector === "string") {
        if (selector.length == 0) {
            return document.body;
        }
        if (selector.startsWith("<") && selector.endsWith(">") && selector.length>3) {
            var div = document.createElement("div");
            div.innerHTML = selector;
            document.body.appendChild(div);
            var el = div.childNodes[0];
            document.body.appendChild(el);
            document.body.removeChild(div);
            return el;
        } else if (selector.startsWith("#")) {
            var el = document.getElementById(selector.substring(1));
            return el;
        } else if (selector.startsWith(":")) {
            var el = document.getElementsByName(selector.substring(1));
            return el[0];
        } else if (selector.startsWith(".")) {
            var el = document.getElementsByClassName(selector.substring(1));
            return el[0];
        } else {
            var el = document.getElementsByTagName(selector);
            return el;
        }
    } else if (selector == window) {
        return selector;
    } else if (selector == document) {
        return selector
    }
};

var Component = window["Component"] = Common["Component"] = class {

    element = new Array();

    constructor(selector, root) {
        var parent;
        if (root) {
            if (root instanceof Component) {
                parent = root.element[0];
            } else {
                parent = root;
            }
        }

        if (typeof selector === "string")  {
            if (selector.startsWith("<") && selector.endsWith(">") && selector.length>3) {
                var div = document.createElement("div");
                div.innerHTML = selector;
                div.childNodes.forEach(el => {
                    (!parent ? document.body : parent).appendChild(el);
                    this.element.push(el);
                });
            } else {
                this.element.push(find(selector));
            }
        } else if (selector == document.body) {
            this.element.push(selector);
        }
        
    }
};

Component.fn = Component.prototype;

Component.fn.forEach = function(callback) {

    this.element.forEach(el => {
        callback(el);
    })
};

Component.fn.append = function(o) {
    if (o instanceof HTMLElement || o instanceof Node) {
        this.element[0].appendChild(o);
    } else if (o instanceof String || typeof(o) === 'string') {
        this.element[0].innerHTML = o;
    } else if (o instanceof Array || typeof(o) === 'array') {
        for (var i in o) {
            this.append(o[i]);
        }
    } else if (o instanceof Function) {
        this.append(o());
    }
}

Component.fn.attr = function(name, value) {
    if (name == null || name == undefined) {
        return null;
    }
    if (value == undefined) {
        return this.element[0].getAttribute(name);
    }
    if (value == null) {
        this.forEach(el => {
            el.removeAttribute(name);
        });
        //this.element.removeAttribute(name);
    } else {
        this.forEach(el => {
            el.setAttribute(name, value);
            console.log("attr", name, value);
        });
        //this.element.setAttribute(name);
    }
};

Component.fn.bind = function(e, callback) {
    if (e == null || typeof(e) == 'undefined' || typeof(callback) == 'undefined') {
        return;
    }
    this.forEach(el  => {
        el.addEventListener(e, callback, false);
        console.log("add envent listener: ", e, callback);
    });
};

Component.fn.unbind = function(e, callback) {
    if (e == null || typeof(e) == 'undefined' || typeof(callback) == 'undefined') {
        return;
    }
    this.forEach(el  => {
        el.removeEventListener(e, callback, false);
        console.log("remove envent listener: ", e, callback);
    });
};

Component.fn.css = function(styleName, styleValue) {
    //console.log("css");
    if (styleName == null || typeof(styleName) == 'undefined') {
        return;
    }
    if (typeof(styleValue) == 'undefined') {
        switch(String(styleName).toLowerCase()) {
            case "left":
                return this.element[0].offsetLeft + "px";
            case "top":
                return this.element[0].offsetTop + "px";
            case "right":
                return (this.element[0].offsetLeft + this.element[0].offsetWidth) + "px";
            case "bottom":
                return (this.element[0].offsetTop + this.element[0].offsetHeight) + "px";
            case "width":
                return this.element[0].offsetWidth + "px";
            case "height":
                return this.element[0].offsetHeight + "px";
            default:
                return this.element[0].style[styleName];
        }
        
    } else if (styleValue == null) {
        //console.log("rmv css", styleName);
        this.forEach(el => {
            el.style.removeProperty(styleName);
        });
    } else {
        this.forEach(el => {
            el.style.setProperty(styleName, styleValue); 
            //console.log("set css", el.style, styleName, styleValue);
        });
    }
};

Component.fn.html = function(html) {
    if (styleValue == undefined) {
        return this.element[0].innerHTML;
    } else {
        this.forEach(el => { 
            el.innerHTML = html;
        });
    }
}

/**
 * 隐藏
 */
Component.fn.hiden = function() {
    this.forEach(el => {
        el.setAttribute("hidden", true);
    });
}

/**
 * 显示
 */
Component.fn.show = function() {
    this.forEach(el => {
        el.removeAttribute("hidden");
        //console.log("show", el);
    });
}

Component.fn.val = function(value) {
    if (styleValue == undefined) {
        return this.element[0].value;
    } else {
        this.forEach(el => {
            el.value = value;
        });
    }
}

Component.fn.parent = function() {
    return this.element[0].parentElement;
}


Component.fn.resizable = function(flag) {
    var that  = this;
    that._selected = false;
    var _mouseover = function(e) {
        var x = e.clientX;
        var y = e.clientY;
        var t = Number(that.css("top").replace("px", ""));
        var l = Number(that.css("left").replace("px", ""));
        var r = Number(that.css("width").replace("px", ""));
        var b = Number(that.css("height").replace("px", ""));
        var a = 
        
    };

    var _mousedown = function(e) {
        that._selected = true;
    };

    var _mouseup = function(e) {
        that._selected = false;
    };

    var _mousemove = function(e) {

    };

}


/**
 * 调节器
 * 用于调整容器宽度和高度。
 * 可以通过 参数 config 指定调节器在容器中的位置，有 top, left, right, bottom 四种选项。
 * 需要注事的是，调节器只能指定一个位置，批定多个位置也只能生效一个。
 * 参数示例：
 * config - {top: true}
 * 
 * @param {*} root
 * @param {*} config - {top: boolean, left: boolean, right: boolean, bottom: boolean}
 * 
 */
var Adjuster = Common["Adjuster"] = class extends Component {

    _parent;

    _top = false;
    _left = false;
    _right = false;
    _bottom = false;
    /** 用于标记调节器是否被选中，被选中的调节点会响应鼠标移动事件。 */
    _selected = false;

    constructor(root, config) {
        super("<div></div>", root);

        if (root instanceof Component) {
            this._parent = root;
        } else {
            this._parent = new Component(root);
        }
       

        this._init(config); 
        this._event();

    }

    _init = function(config) {
        this._top = !config ? false : config["top"]; 
        this._left = !config ? false : config["left"]; 
        this._right = !config ? false : config["right"]; 
        this._bottom = !config ? false : config["bottom"]; 

        if (this._top) { // 顶部
            this.attr("class", "adjuster-top");
        } else if (this._left) { // 左侧
            this.attr("class", "adjuster-left");
        } else if (this._right) { // 右侧
            this.attr("class", "adjuster-right");
        } else if (this._bottom) { // 底部
            this.attr("class", "adjuster-bottom");
        }
    };

    _event = function() {
        var that = this;

        this.bind('mousedown', function(e) {
            that._selected = true;
        });

        document.body.addEventListener('mouseup', function(e) {
            that. _selected = false;
        }, false);

        document.body.addEventListener('mousemove', function(e) {
            if (that._selected) {
                var x = e.clientX;
                var y = e.clientY;
                var t = Number(that._parent.css("top").replace("px", ""));
                var l = Number(that._parent.css("left").replace("px", ""));
                var w = Number(that._parent.css("width").replace("px", ""));
                var h = Number(that._parent.css("height").replace("px", ""));
                if (that._top) {
                    h = t - y + h; 
                    that._parent.css("height", h + "px");
                    //console.log("mouse move top. x: ", x, "y: ", y, w, h, t, l);
                } else if (that._left) {
                    w = l - x + w;
                    that._parent.css("width", w + "px");
                    //console.log("mouse move left. x: ", e.clientX, "y: ", e.clientY, w, h, t, l);
                } else if (that._right) {
                    w = x - l;
                    that._parent.css("width", w + "px");
                    //console.log("mouse move right. x: ", e.clientX, "y: ", e.clientY, w, h, t, l);
                } else if (that._bottom) {
                    h = y - t;
                    that._parent.css("height", h + "px");
                    //console.log("mouse move bottom. x: ", e.clientX, "y: ", e.clientY, w, h, t, l);
                }

                return false;
            }
         }, false);
    }
};
/**
 * 应用配置
 * @param {*} config 
 */
Adjuster.fn.config = function(config) {
    this._init(config);
};


var Panel = Common["Panel"] = class extends Component {

    header;
    body;
    center;
    footer;

    constructor(selector, root) {
        super(selector, root);
        
        this.header = new Component("<div class='panel-header'></div>", this);
        this.adjusterHTM = new Adjuster(this.header, {'bottom': true});
        this.body = new Component("<div class='panel-body'></div>", this);
        this.left = new Component("<div class='panel-left'></div>", this.body);
        this.adjuesterVLM = new Adjuster(this.left, {'right': true});
        this.center = new Component("<div class='panel-center'></div>", this.body);
        this.right = new Component("<div class='panel-right'></div>", this.body);
        this.adjuesterVRM = new Adjuster(this.right, {'left': true});
        this.footer = new Component("<div class='panel-footer'></div>", this);
        this.adjusterHBM = new Adjuster(this.footer, {'top': true});
        this.attr("class", "panel");
        this._observer();
        
        this._init();
    }

    _observer = function() {
        var observer = new MutationObserver(function(mutations, observer) {
            mutations.forEach(function(mutation) { 
                console.log("something changed.", mutation, observer);
                if (mutation.type == "attributes") {
                    console.log("attribute changed.", mutation.target);
                }
            });
        });

        observer.observe(this.body.element[0], {
            attributes: true,
            childList: true,
            attributeFilter: ["style"],

        });
    };

    _init = function() {

    }
};