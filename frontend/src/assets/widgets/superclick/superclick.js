!function(a){var b=function(){var b={bcClass:"sf-breadcrumb",menuClass:"sf-js-enabled",anchorClass:"sf-with-ul",menuArrowClass:"sf-arrows"},c=(function(){a(window).load(function(){a("body").children().on("click.superclick",function(){var b=a(".sf-js-enabled");b.superclick("reset")})})}(),function(a,c){var d=b.menuClass;c.cssArrows&&(d+=" "+b.menuArrowClass),a.toggleClass(d)}),d=function(c,d){return c.find("li."+d.pathClass).slice(0,d.pathLevels).addClass(d.activeClass+" "+b.bcClass).filter(function(){return a(this).children(".sidebar-submenu").hide().show().length}).removeClass(d.pathClass)},e=function(a){a.children("a").toggleClass(b.anchorClass)},f=function(a){var b=a.css("ms-touch-action");b="pan-y"===b?"auto":"pan-y",a.css("ms-touch-action",b)},g=function(b){var c,d=a(this),e=d.siblings(".sidebar-submenu");return e.length?(c=e.is(":hidden")?h:i,a.proxy(c,d.parent("li"))(),!1):void 0},h=function(){var b=a(this);l(b);b.siblings().superclick("hide").end().superclick("show")},i=function(){var b=a(this),c=l(b);a.proxy(j,b,c)()},j=function(b){b.retainPath=a.inArray(this[0],b.$path)>-1,this.superclick("hide"),this.parents("."+b.activeClass).length||(b.onIdle.call(k(this)),b.$path.length&&a.proxy(h,b.$path)())},k=function(a){return a.closest("."+b.menuClass)},l=function(a){return k(a).data("sf-options")};return{hide:function(b){if(this.length){var c=this,d=l(c);if(!d)return this;var e=d.retainPath===!0?d.$path:"",f=c.find("li."+d.activeClass).add(this).not(e).removeClass(d.activeClass).children(".sidebar-submenu"),g=d.speedOut;b&&(f.show(),g=0),d.retainPath=!1,d.onBeforeHide.call(f),f.stop(!0,!0).animate(d.animationOut,g,function(){var b=a(this);d.onHide.call(b)})}return this},show:function(){var a=l(this);if(!a)return this;var b=this.addClass(a.activeClass),c=b.children(".sidebar-submenu");return a.onBeforeShow.call(c),c.stop(!0,!0).animate(a.animation,a.speed,function(){a.onShow.call(c)}),this},destroy:function(){return this.each(function(){var d=a(this),g=d.data("sf-options"),h=d.find("li:has(ul)");return g?(c(d,g),e(h),f(d),d.off(".superclick"),h.children(".sidebar-submenu").attr("style",function(a,b){return b.replace(/display[^;]+;?/g,"")}),g.$path.removeClass(g.activeClass+" "+b.bcClass).addClass(g.pathClass),d.find("."+g.activeClass).removeClass(g.activeClass),g.onDestroy.call(d),void d.removeData("sf-options")):!1})},reset:function(){return this.each(function(){var b=a(this),c=l(b),d=a(b.find("."+c.activeClass).toArray().reverse());d.children("a").trigger("click")})},init:function(h){return this.each(function(){var i=a(this);if(i.data("sf-options"))return!1;var j=a.extend({},a.fn.superclick.defaults,h),k=i.find("li:has(ul)");j.$path=d(i,j),i.data("sf-options",j),c(i,j),e(k),f(i),i.on("click.superclick","a",g),k.not("."+b.bcClass).superclick("hide",!0),j.onInit.call(this)})}}}();a.fn.superclick=function(c,d){return b[c]?b[c].apply(this,Array.prototype.slice.call(arguments,1)):"object"!=typeof c&&c?a.error("Method "+c+" does not exist on jQuery.fn.superclick"):b.init.apply(this,arguments)},a.fn.superclick.defaults={activeClass:"sfHover",pathClass:"overrideThisToUse",pathLevels:1,animation:{opacity:"show"},animationOut:{opacity:"hide"},speed:"normal",speedOut:"fast",cssArrows:!0,onInit:a.noop,onBeforeShow:a.noop,onShow:a.noop,onBeforeHide:a.noop,onHide:a.noop,onIdle:a.noop,onDestroy:a.noop}}(jQuery);