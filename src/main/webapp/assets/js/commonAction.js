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
	      $oinput.each(function(i,that){
	          if($(that).val() == ""+ival){
	        	  $(that).click();
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
							showSuccess('操作成功');	
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
	function deleteItemAction(url,isReload){
		var ids = [];
	    var checkboxs = this.$(".list_table").find("input[type='checkbox']:checked").each(
	        function () {
	            ids.push($(this).val());
	        });
	    if(ids.length<1){
	    	layer.msg('请选择要删除的项', {icon: 1});
	    	return;
	    }
		layer.confirm('确定删除吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					url:url,
					data:"ids="+ids,
					beforeSend:function(){
					  layer.msg('请稍后', {icon: 1});
					},
					success:function(data){
						if(data.code==200){
							showSuccess('操作成功');	
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
	function confirmItemAction(url,isReload){
		var ids = [];
	    var checkboxs = this.$(".list_table").find("input[type='checkbox']:checked").each(
	        function () {
	            ids.push($(this).val());
	        });
	    if(ids.length<1){
	    	layer.msg('请选择要操作的项', {icon: 1});
	    	return;
	    }
	    layer.confirm('确认操作吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					url:url,
					data:"ids="+ids,
					beforeSend:function(){
					  layer.msg('请稍后', {icon: 1});
					},
					success:function(data){
						if(data.code==200){
							showSuccess('操作成功');	
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
							showSuccess('操作成功');	
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
					showSuccess('操作成功');
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
	function dialogPage(title,url,endCall){
		 layer.open({
		      type: 2,
		      title: title,
		      shadeClose: true,
		      shade: [0.1,'#fff'],
		      maxmin: true, //开启最大化最小化按钮
		      area: ['700px', '480px'],
		      content: url,
		      end:function(){
		    	  endCall();
		    	}
		    });
	}
function showSuccess(msg){
	parent.alertSucc(msg);
}
function alertSucc(msg){
	layer.msg(msg, {icon: 1});
}
function getAction(url,success){
		
		$.ajax({
			url:url,
			type:'get',
			beforeSend:function(){
			  //layer.msg('请稍后', {icon: 1});
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