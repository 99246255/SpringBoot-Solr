/*
 *ͼƬ�鿴�����
 */


/*ͼƬ�鿴����������
interface IImageViewerOptions{
    wrpperEl:HTMLElement;                               ͼƬԪ�صĸ�Ԫ��
    imageEl:HTMLImageElement;                           ͼƬԪ��DOM����
    totalImageCount:number;                             ��Ҫչʾ��ͼƬ����
    getImageSrcByIndex:(index:number)=>string;          ������ֵ��ȡͼƬ��ַ
    minScrollDistance?:number;                          �����л�ͼƬ����С��������
    onloadingImage?:()=>void;                           ����ͼƬʱҪִ�еĲ����������ЩloadingЧ��ʲô��
    onloaded?:()=>void;                                  ����ͼƬʱҪִ�еĲ����������ЩloadingЧ��ʲô��
}*/

function ImageViewer(options) {
    this.imageEl = options.imageEl;
    this.initialize(options);
}

ImageViewer.prototype = {

    initialize:function(options) {
        this.options = options;
        this.currentImageIndex = this.options.index;
        this.imageEl.src = this.options.getImageSrcByIndex(this.currentImageIndex);
        this.initEvents();
    },

    initEvents:function() {
        var self = this;
        //if (!this.isSupportTouchEvents()) {
        //    this.initClickEvents();
        //} else {
            this.initTouchEvents();
        //}
        
        //ͼƬ���سɹ��������µ�ǰͼƬ����Ӵ��ĳ�ʼ��Ϣ
        this.imageEl.onload = function() {
            self.initBoundingRectInfo = this.getBoundingClientRect();   
            self.options.onloadedImage && self.options.onloadedImage();
        }
    },

    initClickEvents:function() {
        var self = this;

        this.imageEl.onclick = function(e) {
            var eventX = e.pageX;
            var boundingRectInfo = this.getBoundingClientRect();
            boundingRectInfo.width = boundingRectInfo.width || (boundingRectInfo.right - boundingRectInfo.left);

            var isClickedLeft = (eventX - boundingRectInfo.left) < (boundingRectInfo.width /2);
            var delta = isClickedLeft ? -1 : 1;
            self.loadAdjacentImage(delta);
        }
    },

    initTouchEvents:function() {
        var self = this;
		
        this.minScrollDistance = this.options.minScrollDistance || 20;
        new IScroll(this.options.wrapperEl,{
            zoom: true,
            zoomMin: 1,
            zoomMax: 4,
			scrollX: true,
			scrollY: true,
    
            //onScrollStart:function(e) {
                //self.touchStartX = e.touches[0].pageX;
            //},
            //onScrollMove: function(e) {
                //self.touchMoveX = e.touches[0].pageX;
            //},
            //onScrollEnd: function() {
                //if(self.isImageZoomed()){
                    //return;
                //}
                //var moveDistance = self.touchMoveX - self.touchStartX;
                //if (Math.abs(moveDistance) < self.options.minScrollDistance) {
                    //return;
                //}
                //var delta = moveDistance > 0 ? -1 : 1;
                //self.loadAdjacentImage(delta);
            //}
        });


        this.imageEl.ontouchstart = function(e) {
            self.touchStartX = e.touches[0].pageX;
        };

        this.imageEl.ontouchmove = function(e) {
            e.preventDefault();
            self.isMoving = true;
            self.touchMoveX = e.touches[0].pageX;
        };

        this.imageEl.ontouchend = function() {
            //alert(self.isMoving);
            //alert(!self.isImageZoomed());
            if (self.isMoving && !self.isImageZoomed()) {
                var moveDistance = self.touchMoveX - self.touchStartX;
                if (Math.abs(moveDistance) < self.options.minScrollDistance) {
                    return;
                }
                var delta = moveDistance > 0 ? -1 : 1;
                self.loadAdjacentImage(delta);
                self.isMoving = false;
            }
        }
    },

    /*
     *�������ڵ�һ��ͼƬ
     */
    loadAdjacentImage:function(delta) {
        var targetIndex = this.currentImageIndex + delta;
        if (targetIndex < 0 || targetIndex > this.options.totalImageCount) {
            return;
        }
        this.imageEl.src = this.options.getImageSrcByIndex(targetIndex);
        this.currentImageIndex += delta;
        this.options.onloadingImage && this.options.onloadingImage();
    },

    /*
     *�ж�ͼƬ�Ƿ�������״̬
     */
    isImageZoomed: function() {
        var boundingRectInfo = this.imageEl.getBoundingClientRect();   
        for(var key in boundingRectInfo){
            if(['top','bottom','right','left'].indexOf(key) > -1){
                if (boundingRectInfo[key] !== this.initBoundingRectInfo[key]) {
                    return true;
                }
            }
        }
        return false;
    },

    /*
     *�ж��Ƿ��Ǵ����豸
     */
    isSupportTouchEvents:function() {
        return document.hasOwnProperty("ontouchstart");
    }
}