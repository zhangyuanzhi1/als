/**
 * 自定义js：jquery的扩展
 */
//序列化表单为json对象：serializeJson
	$.fn.serializeJson=function(){  
	    var serializeObj={};  
	    var array=this.serializeArray();  
	    var str=this.serialize();  
	    $(array).each(function(){  
	        if(serializeObj[this.name]){  
	            if($.isArray(serializeObj[this.name])){  
	                serializeObj[this.name].push(this.value);  
	            }else{  
	                serializeObj[this.name]=[serializeObj[this.name],this.value];  
	            }  
	        }else{  
	            serializeObj[this.name]=this.value;   
	        }  
	    });  
	    return serializeObj;  
	}; 