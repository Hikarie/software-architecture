<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Send Email</title>
    <div class="page-header">
        <h1>Send Email</h1>
    </div>
</head>
<body>
<div class="container">
    <form role="form" id="form_send">
        <div class="form-group">
            <label for="input_url">邮箱：</label>
            <input type="text" class="form-control" id="input_url" placeholder="邮箱" name="url"/>
        </div>
        <div class="form-group">
            <label for="input_payload">内容：</label>
            <input type="text" class="form-control" id="input_payload" placeholder="内容" name="payload"/>
        </div>
        <br>
        <button id="submit_vali" type="button" class="btn btn-default">验证</button>
        <button id="submit_send" type="button" class="btn btn-default">发送</button>
    </form>
    <div class="page-header">
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <div id="product"></div>
        </div>
    </div>

</div>

<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script src="http://cdn.bootcss.com/handlebars.js/1.3.0/handlebars.min.js"></script>

<script type="text/x-handlebars-template" id="product_table_template">
    {{#if data}}
    <div>successful!</div>
    {{else}}
    <div>Can not find any data!</div>
    {{/if}}
</script>

<script>
    $(function() {
        //提交表单，根据名称检索
        $("#submit_send").click(function(){
            $("#product").html("");
            $.ajax({
                type: 'get',
                url: 'http://localhost:8080/email/ws/rest/email/sendEmail?'+$("#form_send").serialize(),
                dataType: 'json',
                success: function(data) {
                    var template = $("#product_table_template").html();
                    var render = Handlebars.compile(template);
                    var html = render({
                        data: data
                    });
                    $('#product').html(html);
                }
            });
        });

        $("#submit_vali").click(function(){
            $("#product").html("");
            $.ajax({
                type: 'get',
                url: 'http://localhost:8080/email/ws/rest/email/validateEmailAddress?'+$("#input_url").serialize(),
                dataType: 'json',
                success: function(data) {
                    var template = $("#product_table_template").html();
                    var render = Handlebars.compile(template);
                    var html = render({
                        data: data
                    });
                    $('#product').html(html);
                }
            });
        });

    });

</script>
</body>
</html>