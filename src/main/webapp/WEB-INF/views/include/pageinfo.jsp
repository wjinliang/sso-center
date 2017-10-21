
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<div class="page mt10">
					<div class="pagination">
						<ul>
							<li class="first-child ${(page.isFirstPage||page.isLastPage)?'disabled':'' }"><a
								href="javascript:nextPage('${page.firstPage }')">首页</a></li>
							<li class="${(page.isFirstPage||page.isLastPage)?'disabled':'' }"><a
								href="javascript:nextPage('${page.prePage }')">上一页</a></li>
							<li class="${page.isLastPage?'disabled':'' }"><a
								href="javascript:nextPage('${page.nextPage }')">下一页</a></li>
							<li class="last-child ${page.isLastPage?'disabled':'' }"><a
								href="javascript:nextPage('${page.lastPage }')">末页</a></li>
								<li style="line-height: 40px;margin-left: 10px;">第${page.pageNum }页/共${page.pages }页（共${page.total }条记录）</span></li>
						</ul>
					</div>
				</div>
