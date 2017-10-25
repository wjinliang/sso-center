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
							window.location.reload();
						}
						else{
							layer.msg('删除失败', {icon: 1});
						}
					},
					error:function(){
						layer.msg('操作失败', {icon: 1});
					}
				});
			}, function(){
			  return;
			});
	};
	function conformAction(url,isReload){
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
							window.location.reload();
						}
						else{
							layer.msg('操作失败', {icon: 1});
						}
					},
					error:function(){
						layer.msg('操作失败', {icon: 1});
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
					if(!isReload){
						window.location.reload();
					}
				}
				else{
					layer.msg('操作失败', {icon: 1});
				}
			},
			error:function(){
				layer.msg('操作失败', {icon: 1});
			}
		});
		
	};