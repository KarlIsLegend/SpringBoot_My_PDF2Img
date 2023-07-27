<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Freemarker</title>
</head>
<#--<link rel="icon" href="./favicon.ico" type="image/x-icon">-->
<#--<link rel="stylesheet" href="css/viewer.min.css"/>-->
<#--<link rel="stylesheet" href="css/loading.css"/>-->
<#--<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>-->
<#--<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css"/>-->
<#--<link rel="stylesheet" href="bootstrap-table/bootstrap-table.min.css"/>-->
<#--<link rel="stylesheet" href="css/theme.css"/>-->
<#--<script type="text/javascript" src="js/jquery-3.6.1.min.js"></script>-->
<#--<script type="text/javascript" src="js/jquery.form.min.js"></script>-->
<#--<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>-->
<#--<script type="text/javascript" src="bootstrap-table/bootstrap-table.min.js"></script>-->
<#--<script type="text/javascript" src="js/base64.min.js"></script>-->
<body>
<h1>${msg}</h1>
<div class="panel-body">
    <#if fileUploadDisable == false>
        <div style="padding: 10px" >
            <form enctype="multipart/form-data" id="fileUpload">
                <input type="file" id="size" name="file"/>
                <input type="button" id="btnSubmit" value=" 上 传 "/>
            </form>
        </div>
    </#if>
    <div>
        <table id="table" data-pagination="true"></table>
    </div>
</div>
<div>
    <img src="${d1}/${date}/preview.jpg" id="img" style="width:80px;height:80px;" alt="预览图片"/>
</div>
<input type="file" id="file" onclick="show()"/>
<script type="text/javascript" src="jquery-3.2.1.js"></script>
<script type="text/javascript">
    function show() {
        const fileTag = document.getElementById('file');
        fileTag.onchange = function () {
            const file = fileTag.files[0];
            const fileReader = new FileReader();
            fileReader.onloadend = function () {
                if (fileReader.readyState === fileReader.DONE) {
                    document.getElementById('img').setAttribute('src', fileReader.result.toString());
                }
            };
            fileReader.readAsDataURL(file);
        };
    }
</script>
</body>
</html>

