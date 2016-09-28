define(function (require, exports, module) {
	/**
	 * 修改自jQuery jPages v0.7
	 * Client side pagination with jQuery
	 * http://luis-almeida.github.com/jPages
	 *
	 * Licensed under the MIT license.
	 * Copyright 2012 Luís Almeida
	 * https://github.com/luis-almeida
	 */
	
	;(function($, window, document, undefined) {
	
	  var name = "vPages",
	      instance = null,
	      defaults = {
	  		    container: null,                //需要实现分页的容器元素，可以是div或者ul，ol
	  		    count: 0,                       //分页数量
	  	        first: false,                   //自定义”首页“button是否显示，缺省为false，如果传递字符串，则显示为“首页”文字
	  	        previous: "← previous",         //自定义”上一页“button是否显示
	  	        next: "next →",                 //同上是否显示”下一页“button
	  	        last: false,                    //是否显示”尾页“button
	  	        links: "numeric", // blank || title
	  	        startPage: 1,                   //需要显示的开始页数，缺省为”1“
	  	        perPage: 10,                    //每页显示的项目数，缺省为”10“
	  	        midRange: 5,                    //显示包含当前页数显示页面数量，缺省为”5“
	  	        startRange: 1,                  //显示开始显示的页面数，无论目前你处于哪个页面，缺省”1”
	  	        endRange: 1,                    //显示末尾显示的页面数，无论目前你处于哪个页面，缺省”1”
	  	        keyBrowse: false,
	  	        scrollBrowse: false,
	  	        pause: 0,
	  	        clickStop: false,
	  	        delay: 50,
	  	        direction: "forward", // backwards || auto || random ||如果不使用CSS3动画，你可以使用fallback来设定jQuery的淡入效果的速度
	  	        animation: "", // http://daneden.me/animate/ - any entrance animations使用Animate.css的动画效果，当然需要添加css3类库支持
	  	        fallback: 400,
	  	        minHeight: true,
	  	        callback: undefined // 回调函数function( pages, items ) { }，pages对象属性，pages.current，pages.interval，pages.count
  	      };
	
	
	  function Plugin(element, options) {
	    this.options = $.extend({}, defaults, options);
	
	    this._container = $(this.options.container);
	    if (!this._container.length) return;
	
	    this.jQwindow = $(window);
	    this.jQdocument = $(document);
	
	    this._holder = $(element);
	    this._nav = {};
	
	    this._first = $(this.options.first);
	    this._previous = $(this.options.previous);
	    this._next = $(this.options.next);
	    this._last = $(this.options.last);
	
	    this._items = this._container.children(":visible");
	    this._itemsShowing = $([]);
	    this._itemsHiding = $([]);
	    
	    if(!this.options.count){
	    	this._numPages = Math.ceil(this._items.length / this.options.perPage);
	    }else{
	    	this._numPages = this.options.count;
	    }
	    
	    this._currentPageNum = this.options.startPage;
	
	    this._clicked = false;
	    this._cssAnimSupport = this.getCSSAnimationSupport();
	
	    this.init();
	  }
	
	  Plugin.prototype = {
	
	    constructor : Plugin,
	
	    getCSSAnimationSupport : function() {
	      var animation = false,
	          animationstring = 'animation',
	          keyframeprefix = '',
	          domPrefixes = 'Webkit Moz O ms Khtml'.split(' '),
	          pfx = '',
	          elm = this._container.get(0);
	
	      if (elm.style.animationName) animation = true;
	
	      if (animation === false) {
	        for (var i = 0; i < domPrefixes.length; i++) {
	          if (elm.style[domPrefixes[i] + 'AnimationName'] !== undefined) {
	            pfx = domPrefixes[i];
	            animationstring = pfx + 'Animation';
	            keyframeprefix = '-' + pfx.toLowerCase() + '-';
	            animation = true;
	            break;
	          }
	        }
	      }
	
	      return animation;
	    },
	
	    init : function() {
	      this.setStyles();
	      this.setNav();
	      this.paginate(this._currentPageNum);
	      this.setMinHeight();
	    },
	
	    setStyles : function() {
	      /*var requiredStyles = "<style>" +
	      ".vp_invisible { visibility: hidden !important; } " +
	      ".vp_hidden { display: none !important; }" +
	      "</style>";
	
	      $(requiredStyles).appendTo("head");*/
	
	      if (this._cssAnimSupport && this.options.animation.length)
	        this._items.addClass("animated vp_hidden");
	      else this._items.hide();
	
	    },
	
	    setNav : function() {
	      var navhtml = this.writeNav();
	
	      this._holder.each(this.bind(function(index, element) {
	        var holder = $(element);
	        holder.html(navhtml);
	        this.cacheNavElements(holder, index);
	        this.bindNavHandlers(index);
	        this.disableNavSelection(element);
	      }, this));
	
	      if (this.options.keyBrowse) this.bindNavKeyBrowse();
	      if (this.options.scrollBrowse) this.bindNavScrollBrowse();
	    },
	
	    writeNav : function() {
	      var i = 1, navhtml;
	      navhtml = this.writeBtn("first") + this.writeBtn("previous");
	
	      for (; i <= this._numPages; i++) {
	        if (i === 1 && this.options.startRange === 0) navhtml += "<span>...</span>";
	        if (i > this.options.startRange && i <= this._numPages - this.options.endRange)
	          navhtml += "<a href='#' class='vp_hidden'>";
	        else
	          navhtml += "<a>";
	
	        switch (this.options.links) {
	          case "numeric":
	            navhtml += i;
	            break;
	          case "blank":
	            break;
	          case "title":
	            var title = this._items.eq(i - 1).attr("data-title");
	            navhtml += title !== undefined ? title : "";
	            break;
	        }
	
	        navhtml += "</a>";
	        if (i === this.options.startRange || i === this._numPages - this.options.endRange)
	          navhtml += "<span>...</span>";
	      }
	      navhtml += this.writeBtn("next") + this.writeBtn("last") + "</div>";
	      return navhtml;
	    },
	
	    writeBtn : function(which) {
	
	      return this.options[which] !== false && !$(this["_" + which]).length ?
	      "<a class='vp_" + which + "'>" + this.options[which] + "</a>" : "";
	
	    },
	
	    cacheNavElements : function(holder, index) {
	      this._nav[index] = {};
	      this._nav[index].holder = holder;
	      this._nav[index].first = this._first.length ? this._first : this._nav[index].holder.find("a.vp_first");
	      this._nav[index].previous = this._previous.length ? this._previous : this._nav[index].holder.find("a.vp_previous");
	      this._nav[index].next = this._next.length ? this._next : this._nav[index].holder.find("a.vp_next");
	      this._nav[index].last = this._last.length ? this._last : this._nav[index].holder.find("a.vp_last");
	      this._nav[index].fstBreak = this._nav[index].holder.find("span:first");
	      this._nav[index].lstBreak = this._nav[index].holder.find("span:last");
	      this._nav[index].pages = this._nav[index].holder.find("a").not(".vp_first, .vp_previous, .vp_next, .vp_last");
	      this._nav[index].permPages =
	        this._nav[index].pages.slice(0, this.options.startRange)
	          .add(this._nav[index].pages.slice(this._numPages - this.options.endRange, this._numPages));
	      this._nav[index].pagesShowing = $([]);
	      this._nav[index].currentPage = $([]);
	    },
	
	    bindNavHandlers : function(index) {
	      var nav = this._nav[index];
	
	      // default nav
	      nav.holder.bind("click.vkoPages", this.bind(function(evt) {
	        var newPage = this.getNewPage(nav, $(evt.target));
	        if (this.validNewPage(newPage)) {
	          this._clicked = true;
	          this.paginate(newPage);
	        }
	        evt.preventDefault();
	      }, this));
	
	      // custom first
	      if (this._first.length) {
	        this._first.bind("click.vkoPages", this.bind(function() {
	          if (this.validNewPage(1)) {
	            this._clicked = true;
	            this.paginate(1);
	          }
	        }, this));
	      }
	
	      // custom previous
	      if (this._previous.length) {
	        this._previous.bind("click.vkoPages", this.bind(function() {
	          var newPage = this._currentPageNum - 1;
	          if (this.validNewPage(newPage)) {
	            this._clicked = true;
	            this.paginate(newPage);
	          }
	        }, this));
	      }
	
	      // custom next
	      if (this._next.length) {
	        this._next.bind("click.vkoPages", this.bind(function() {
	          var newPage = this._currentPageNum + 1;
	          if (this.validNewPage(newPage)) {
	            this._clicked = true;
	            this.paginate(newPage);
	          }
	        }, this));
	      }
	
	      // custom last
	      if (this._last.length) {
	        this._last.bind("click.vkoPages", this.bind(function() {
	          if (this.validNewPage(this._numPages)) {
	            this._clicked = true;
	            this.paginate(this._numPages);
	          }
	        }, this));
	      }
	
	    },
	
	    disableNavSelection : function(element) {
	      if (typeof element.onselectstart != "undefined")
	        element.onselectstart = function() {
	          return false;
	        };
	      else if (typeof element.style.MozUserSelect != "undefined")
	        element.style.MozUserSelect = "none";
	      else
	        element.onmousedown = function() {
	          return false;
	        };
	    },
	
	    bindNavKeyBrowse : function() {
	      this.jQdocument.bind("keydown.vkoPages", this.bind(function(evt) {
	        var target = evt.target.nodeName.toLowerCase();
	        if (this.elemScrolledIntoView() && target !== "input" && target != "textarea") {
	          var newPage = this._currentPageNum;
	
	          if (evt.which == 37) newPage = this._currentPageNum - 1;
	          if (evt.which == 39) newPage = this._currentPageNum + 1;
	
	          if (this.validNewPage(newPage)) {
	            this._clicked = true;
	            this.paginate(newPage);
	          }
	        }
	      }, this));
	    },
	
	    elemScrolledIntoView : function() {
	      var docViewTop, docViewBottom, elemTop, elemBottom;
	      docViewTop = this.jQwindow.scrollTop();
	      docViewBottom = docViewTop + this.jQwindow.height();
	      elemTop = this._container.offset().top;
	      elemBottom = elemTop + this._container.height();
	      return ((elemBottom >= docViewTop) && (elemTop <= docViewBottom));
	
	      // comment above and uncomment below if you want keyBrowse to happen
	      // only when container is completely visible in the page
	      /*return ((elemBottom >= docViewTop) && (elemTop <= docViewBottom) &&
	                (elemBottom <= docViewBottom) &&  (elemTop >= docViewTop) );*/
	    },
	
	    bindNavScrollBrowse : function() {
	      this._container.bind("mousewheel.vkoPages DOMMouseScroll.vkoPages", this.bind(function(evt) {
	        var newPage = (evt.originalEvent.wheelDelta || -evt.originalEvent.detail) > 0 ?
	        (this._currentPageNum - 1) : (this._currentPageNum + 1);
	        if (this.validNewPage(newPage)) {
	          this._clicked = true;
	          this.paginate(newPage);
	        }
	        evt.preventDefault();
	        return false;
	      }, this));
	    },
	
	    getNewPage : function(nav, target) {
	      if (target.is(nav.currentPage)) return this._currentPageNum;
	      if (target.is(nav.pages)) return nav.pages.index(target) + 1;
	      if (target.is(nav.first)) return 1;
	      if (target.is(nav.last)) return this._numPages;
	      if (target.is(nav.previous)) return nav.pages.index(nav.currentPage);
	      if (target.is(nav.next)) return nav.pages.index(nav.currentPage) + 2;
	    },
	
	    validNewPage : function(newPage) {
	      return newPage !== this._currentPageNum && newPage > 0 && newPage <= this._numPages;
	    },
	
	    paginate : function(page) {
	      var itemRange, pageInterval;
	      itemRange = this.updateItems(page);
	      pageInterval = this.updatePages(page);
	      this._currentPageNum = page;
	      if ($.isFunction(this.options.callback))
	        this.callback(page, itemRange, pageInterval);
	
	      this.updatePause();
	    },
	
	    updateItems : function(page) {
	      var range = this.getItemRange(page);
	      this._itemsHiding = this._itemsShowing;
	      this._itemsShowing = this._items.slice(range.start, range.end);
	      if (this._cssAnimSupport && this.options.animation.length) this.cssAnimations(page);
	      else this.jQAnimations(page);
	      return range;
	    },
	
	    getItemRange : function(page) {
	      var range = {};
	      range.start = (page - 1) * this.options.perPage;
	      range.end = range.start + this.options.perPage;
	      if (range.end > this._items.length) range.end = this._items.length;
	      return range;
	    },
	
	    cssAnimations : function(page) {
	      clearInterval(this._delay);
	
	      this._itemsHiding
	        .removeClass(this.options.animation + " vp_invisible")
	        .addClass("vp_hidden");
	
	      this._itemsShowing
	        .removeClass("vp_hidden")
	        .addClass("vp_invisible");
	
	      this._itemsOriented = this.getDirectedItems(page);
	      this._index = 0;
	
	      this._delay = setInterval(this.bind(function() {
	        if (this._index === this._itemsOriented.length) clearInterval(this._delay);
	        else {
	          this._itemsOriented
	          .eq(this._index)
	          .removeClass("vp_invisible")
	          .addClass(this.options.animation);
	        }
	        this._index = this._index + 1;
	      }, this), this.options.delay);
	    },
	
	    jQAnimations : function(page) {
	      clearInterval(this._delay);
	      this._itemsHiding.addClass("vp_hidden");
	      this._itemsShowing.fadeTo(0, 0).removeClass("vp_hidden");
	      this._itemsOriented = this.getDirectedItems(page);
	      this._index = 0;
	      this._delay = setInterval(this.bind(function() {
	        if (this._index === this._itemsOriented.length) clearInterval(this._delay);
	        else {
	          this._itemsOriented
	          .eq(this._index)
	          .fadeTo(this.options.fallback, 1);
	        }
	        this._index = this._index + 1;
	      }, this), this.options.delay);
	    },
	
	    getDirectedItems : function(page) {
	      var itemsToShow;
	
	      switch (this.options.direction) {
	        case "backwards":
	          itemsToShow = $(this._itemsShowing.get().reverse());
	          break;
	        case "random":
	          itemsToShow = $(this._itemsShowing.get().sort(function() {
	            return (Math.round(Math.random()) - 0.5);
	          }));
	          break;
	        case "auto":
	          itemsToShow = page >= this._currentPageNum ?
	          this._itemsShowing : $(this._itemsShowing.get().reverse());
	          break;
	        default:
	          itemsToShow = this._itemsShowing;
	      }
	
	      return itemsToShow;
	    },
	
	    updatePages : function(page) {
	      var interval, index, nav;
	      interval = this.getInterval(page);
	      for (index in this._nav) {
	        if (this._nav.hasOwnProperty(index)) {
	          nav = this._nav[index];
	          this.updateBtns(nav, page);
	          this.updateCurrentPage(nav, page);
	          this.updatePagesShowing(nav, interval);
	          this.updateBreaks(nav, interval);
	        }
	      }
	      return interval;
	    },
	
	    getInterval : function(page) {
	      var neHalf, upperLimit, start, end;
	      neHalf = Math.ceil(this.options.midRange / 2);
	      upperLimit = this._numPages - this.options.midRange;
	      start = page > neHalf ? Math.max(Math.min(page - neHalf, upperLimit), 0) : 0;
	      end = page > neHalf ?
	        Math.min(page + neHalf - (this.options.midRange % 2 > 0 ? 1 : 0), this._numPages) :
	        Math.min(this.options.midRange, this._numPages);
	      return {start: start,end: end};
	    },
	
	    updateBtns : function(nav, page) {
	      if (page === 1) {
	        nav.first.addClass("vp_disabled");
	        nav.previous.addClass("vp_disabled");
	      }
	      if (page === this._numPages) {
	        nav.next.addClass("vp_disabled");
	        nav.last.addClass("vp_disabled");
	      }
	      if (this._currentPageNum === 1 && page > 1) {
	        nav.first.removeClass("vp_disabled");
	        nav.previous.removeClass("vp_disabled");
	      }
	      if (this._currentPageNum === this._numPages && page < this._numPages) {
	        nav.next.removeClass("vp_disabled");
	        nav.last.removeClass("vp_disabled");
	      }
	    },
	
	    updateCurrentPage : function(nav, page) {
	      nav.currentPage.removeClass("vp_current");
	      nav.currentPage = nav.pages.eq(page - 1).addClass("vp_current");
	    },
	
	    updatePagesShowing : function(nav, interval) {
	      var newRange = nav.pages.slice(interval.start, interval.end).not(nav.permPages);
	      nav.pagesShowing.not(newRange).addClass("vp_hidden");
	      newRange.not(nav.pagesShowing).removeClass("vp_hidden");
	      nav.pagesShowing = newRange;
	    },
	
	    updateBreaks : function(nav, interval) {
	      if (
	        interval.start > this.options.startRange ||
	        (this.options.startRange === 0 && interval.start > 0)
	      ) nav.fstBreak.removeClass("vp_hidden");
	      else nav.fstBreak.addClass("vp_hidden");
	
	      if (interval.end < this._numPages - this.options.endRange) nav.lstBreak.removeClass("vp_hidden");
	      else nav.lstBreak.addClass("vp_hidden");
	    },
	
	    callback : function(page, itemRange, pageInterval) {
	      var pages = {
	            current: page,
	            interval: pageInterval,
	            count: this._numPages
	          },
	          items = {
	            showing: this._itemsShowing,
	            oncoming: this._items.slice(itemRange.start + this.options.perPage, itemRange.end + this.options.perPage),
	            range: itemRange,
	            count: this._items.length
	          };
	
	      pages.interval.start = pages.interval.start + 1;
	      items.range.start = items.range.start + 1;
	      this.options.callback(pages, items);
	    },
	
	    updatePause : function() {
	      if (this.options.pause && this._numPages > 1) {
	        clearTimeout(this._pause);
	        if (this.options.clickStop && this._clicked) return;
	        else {
	          this._pause = setTimeout(this.bind(function() {
	            this.paginate(this._currentPageNum !== this._numPages ? this._currentPageNum + 1 : 1);
	          }, this), this.options.pause);
	        }
	      }
	    },
	
	    setMinHeight : function() {
	      if (this.options.minHeight && !this._container.is("table, tbody")) {
	        setTimeout(this.bind(function() {
	          this._container.css({ "min-height": this._container.css("height") });
	        }, this), 1000);
	      }
	    },
	
	    bind : function(fn, me) {
	      return function() {
	        return fn.apply(me, arguments);
	      };
	    },
	
	    destroy : function() {
	      this.jQdocument.unbind("keydown.vkoPages");
	      this._container.unbind("mousewheel.vkoPages DOMMouseScroll.vkoPages");
	
	      if (this.options.minHeight) this._container.css("min-height", "");
	      if (this._cssAnimSupport && this.options.animation.length)
	        this._items.removeClass("animated vp_hidden vp_invisible " + this.options.animation);
	      else this._items.removeClass("vp_hidden").fadeTo(0, 1);
	      this._holder.unbind("click.vkoPages").empty();
	    }
	
	  };
	
	  $.fn[name] = function(arg) {
	    var type = $.type(arg);
	
	    if (type === "object") {
	      if (this.length && !$.data(this, name)) {
	        instance = new Plugin(this, arg);
	        this.each(function() {
	          $.data(this, name, instance);
	        });
	      }
	      return this;
	    }
	
	    if (type === "string" && arg === "destroy") {
	      instance.destroy();
	      this.each(function() {
	        $.removeData(this, name);
	      });
	      return this;
	    }
	
	    if (type === 'number' && arg % 1 === 0) {
	      if (instance.validNewPage(arg)) instance.paginate(arg);
	      return this;
	    }
	
	    return this;
	  };
	
	})(jQuery, window, document);
});