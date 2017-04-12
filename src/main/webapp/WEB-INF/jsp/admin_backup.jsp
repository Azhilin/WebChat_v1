<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
    </script>
    <script>
        function getUserList() {
            $.ajax({
                type:'GET',
                contentType:'application/json',
                url:'${sessionScope.usersURL}',
                dataType:'json',
                success: function (data, textStatus, jqXHR) {
                    var allUsersArray = data.users;
                    var output = '<form>';
                    for(var i = 0; i<allUsersArray.length; i++){
                        var login = allUsersArray[i].login;
                        var rel = allUsersArray[i].links[0].rel;
                        var href = allUsersArray[i].links[0].href;
                        output += login + '&nbsp'
                        + '<input type="button" '
                                + 'class="banUser" '
                                + 'value=' + '"' + rel + '" '
                                + 'name=' + '"' + login + '" '
                                + 'id=' + '"' + href + '" '
                                + '/>' + '<br>'
                    }
                    output += '</form>';
                    document.getElementById('userList').innerHTML = output;
                    var buttonArray = document.getElementsByClassName('banUser');
                    for(var i = 0; i < buttonArray.length; i++){
                        buttonArray[i].addEventListener('click', banUserHandler);
                    }
                }
            });
        }

        function banUserHandler() {
            var login = $(this).attr('name');
            var href = $(this).attr('id');
            var rel = $(this).attr('value');
            if(rel == 'add'){
                addBanUser(login, href);
            } else {
                deleteBanUser(login, href);
            }
        }

        function addBanUser(login, href) {
            $.ajax({
                type:'POST',
                contentType:'application/json',
                url:href,
                data:JSON.stringify({
                    'login':login,
                }),
                success:function (data, textStatus, jqXHR) {
                    getUserList();
                }
            });
        }

        function deleteBanUser(login, href) {
            $.ajax({
                type:'DELETE',
                contentType:'application/json',
                url:href,
                success:function (data, textStatus, jqXHR) {
                    getUserList();
                }
            });
        }
        window.onload = function () {
            getUserList();
        }
    </script>
</head>
<body>
<div id="userList">

</div>
</body>
</html>
