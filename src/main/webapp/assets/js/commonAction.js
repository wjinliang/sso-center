/**
 * 将josn对象赋值给form
 * @param {dom} 指定的选择器
 * @param {obj} 需要给form赋值的json对象
 * @method serializeJson
 * */
	$.fn.setForm = function(jsonValue){
	  var obj = this;
	  $.each(jsonValue,function(name,ival){
	    var $oinput = obj.find("input[name="+name+"]");
	    if($oinput.attr("type")=="checkbox"){
	      if(ival !== null){
	        var checkboxObj = $("[name="+name+"]");
	        var checkArray = ival.split(",");
	        for(var i=0;i<checkboxObj.length;i++){
	          for(var j=0;j<checkArray.length;j++){
	            if(checkboxObj[i].value == checkArray[j]){
	              checkboxObj[i].click();
	            }
	          }
	        }
	      }
	    }
	    else if($oinput.attr("type")=="radio"){
	      $oinput.each(function(){
	        var radioObj = $("[name="+name+"]");
	        for(var i=0;i<radioObj.length;i++){
	          if(radioObj[i].value == ival){
	            radioObj[i].click();
	          }
	        }
	      });
	    }
	    else if($oinput.attr("type")=="textarea"){
	      obj.find("[name="+name+"]").html(ival);
	    }
	    else{
	      obj.find("[name="+name+"]").val(ival);
	    }
	  })
	}

	function viewPage(url){
		if(url.indexOf('detail=1')==-1){
			if(url.indexOf('?')==-1){
				url+='?detail=1';
			}else{
				url+='&detail=1';
			}
		}
		window.location.href=url;
	}
	
	function openPage(url){
		window.location.href=url;
	}
	function deleteAction(url,isReload){
		layer.confirm('确定删除吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					url:url,
					beforeSend:function(){
					  layer.msg('请稍后', {icon: 1});
					},
					success:function(data){
						if(data.code==200){
							layer.msg('操作成功', {icon: 1});	
							if(isReload||isReload===undefined){
								window.location.reload();
							}
						}
						else if(data.code==301){
							window.location.href=root+data.data;
						}
						else{
							layer.msg('删除失败', {icon: 1});
							//window.location.reload();
						}
					},
					error:function(){
						layer.msg('操作失败', {icon: 1});
						//window.location.reload();
					}
				});
			}, function(){
			  return;
			});
	};
	function confirmAction(url,isReload){
		layer.confirm('确认操作吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					url:url,
					beforeSend:function(){
					  layer.msg('请稍后', {icon: 1});
					},
					success:function(data){
						if(data.code==200){
							layer.msg('操作成功', {icon: 1});	
							if(isReload||isReload===undefined){
								window.location.reload();
							}
							}
						else if(data.code==301){
							window.location.href=root+data.data;
						}
						else{
							layer.msg('操作失败', {icon: 1});
							//window.location.reload();
						}
					},
					error:function(){
						layer.msg('操作失败', {icon: 1});
						//window.location.reload();
					}
				});
			}, function(){
			  return;
			});
	};
	function postAction(url,isReload){
		
		$.ajax({
			url:url,
			type:'post',
			beforeSend:function(){
			  layer.msg('请稍后', {icon: 1});
			},
			success:function(data){
				if(data.code==200){
					layer.msg('操作成功', {icon: 1});
					if(isReload||isReload===undefined){
						window.location.reload();
					}}
				else if(data.code==301){
					window.location.href=root+data.data;
				}
				else{
					layer.msg('操作失败', {icon: 1});
					//window.location.reload();
				}
			},
			error:function(){
				layer.msg('操作失败', {icon: 1});
				//window.location.reload();
			}
		});
		
	};
function getAction(url,success){
		
		$.ajax({
			url:url,
			type:'post',
			beforeSend:function(){
			  layer.msg('请稍后', {icon: 1});
			},
			success:function(data){
				if(data.code==200){
					success(data);
				}
				else if(data.code==301){
					window.location.href=root+data.data;
				}else{
					layer.msg('操作失败', {icon: 1});
					
				}
			},
			error:function(){
				layer.msg('操作失败', {icon: 1});
				//window.location.reload();
			}
		});
		
	};