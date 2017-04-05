<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="panel-group" id="accordion">
	<div class="main-title">
	<h3 class="title-1">
		<a data-toggle="collapse" data-parent="#accordion" 
			href="#collapseOne">
            <span class="glyphicon glyphicon-chevron-right right"></span>
				按关键字筛选
        </a>
    </h3>
    </div>
    <div id="collapseOne" class="collapse">
        <div class="panel-body">
        	<span class="glyphicon glyphicon-arrow-right"></span>
			交通事故
        </div>
        <div class="panel-body">
        	<span class="glyphicon glyphicon-arrow-right"></span>
        	交通事故
        </div>
        <div class="panel-body" id="a2">
            <h4 class="title-1">
                <a data-toggle="collapse" data-parent="#a2" 
                	href="#c2">
                	<span class="glyphicon glyphicon-chevron-right right"></span>
                		按关键字筛选
                </a>
            </h4>
        	<div id="c2" class="collapse">
            	<div class="panel-body">
                	交通事故
            	</div>
            	<div class="panel-body">
                	交通事故
            	</div>
            	<div class="panel-body">
                	交通事故
            	</div>
        	</div>
        </div>
    </div>
</div>
