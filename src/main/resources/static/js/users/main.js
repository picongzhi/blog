'use strict';

$(function () {
    var _pageSize;

    function getUserByName(pageIndex, pageSize) {
        $.ajax({
            url: '/users',
            contentType: 'application/json',
            data: {
                'async': true,
                'pageIndex': pageIndex,
                'pageSize': pageSize,
                'name': $('#searchName').val()
            },
            success: function (data) {
                $('#mainContainer').html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        });
    }

    // 分页
    $.tbpage('#mainContainer', function (pageIndex, pageSize) {
        getUserByName(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    // 搜索
    $('#searchNameBtn').click(function () {
        getUserByName(0, _pageSize);
    });

    // 获取用户添加界面
    $('#addUser').click(function () {
        $.ajax({
            url: '/users/add',
            success: function (data) {
                $('#userFormContainer').html(data);
            },
            error: function (data) {
                toastr.error('error!');
            }
        });
    });

    // 获取用户编辑界面
    $('#rightContainer').on('click', '.blog-edit-user', function () {
        $.ajax({
            url: '/users/edit/' + $(this).attr('userId'),
            success: function (data) {
                $('#userFormContainer').html(data);
            },
            error: function () {
                toastr.error('error!');
            }
        });
    });

    // 提交变更后，清空表单
    $('#submitEdit').click(function () {
        $.ajax({
            url: '/users',
            type: 'POST',
            data: $('#userForm').serialize(),
            success: function (data) {
                $('#userForm')[0].reset();
                if (data.success) {
                    getUserByName(0, _pageSize);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error('error!');
            }
        });
    });

    // 删除用户
    $('#rightContainer').on('click', '.blog-delete-user', function () {
        // var csrfToken = $("meta[name='_csrf']").attr('content');
        // var csrfHeader = $("meta[name='_csrf_header']").attr('content');

        $.ajax({
            url: '/users/' + $(this).attr('userId'),
            type: 'DELETE',
            // beforeSend: function (request) {
            //     request.setRequestHeader(csrfHeader, csrfToken);
            // },
            success: function (data) {
                if (data.success) {
                    getUserByName(0, _pageSize);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error('error!');
            }
        });
    });
});